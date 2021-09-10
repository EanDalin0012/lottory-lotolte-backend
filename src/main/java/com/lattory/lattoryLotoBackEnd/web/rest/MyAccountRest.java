package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.implement.DeviceInfoService;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/my/account")
public class MyAccountRest {
    private static final Logger log = LoggerFactory.getLogger(MyAccountRest.class);

    final DeviceInfoService deviceInfoService;
    final AccountService accountService;
    MyAccountRest(DeviceInfoService deviceInfoService, AccountService accountService) {
        this.deviceInfoService = deviceInfoService;
        this.accountService = accountService;
    }

    @PostMapping(value = "/v0/inquiry")
    public ResponseData accountInquiry(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        JsonObject json = new JsonObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info("jsonObject :"+objectMapper.writeValueAsString(jsonObject));
            JsonObject deviceInfo = new JsonObject();
            deviceInfo.setInt("userID", userID);
            JsonObjectArray jsonObjectArray = deviceInfoService.inquiry(deviceInfo);

            if(jsonObject.getInt("userID") == 0 ) {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_UserID");
                responseData.setResult(header);
                return responseData;
            }
            JsonObject userInput = new JsonObject();
            userInput.setInt("userID", jsonObject.getInt("userID"));
            JsonObject accountObj = accountService.inquiryAccountByUserID(userInput);

            int mainAccountId = accountObj.getInt("id");
            log.info("main account id:"+mainAccountId);
            JsonObject inquirySubAccount = new JsonObject();
            inquirySubAccount.setInt("mainAccountID", mainAccountId);
            JsonObjectArray subAccounts = accountService.inquirySubAccount(inquirySubAccount);


            json.setJsonObjectArray("deviceInfos",jsonObjectArray);
            json.setJsonObject("accountInfo", accountObj);
            json.setJsonObjectArray("subAccounts", subAccounts);

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }
        responseData.setResult(header);
        responseData.setBody(json);
        return responseData;
    }
}
