package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/sub/account/v0")
public class SubAccountRest {
    private static final Logger log = LoggerFactory.getLogger(SubAccountRest.class);

    final AccountService accountService;
    SubAccountRest(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping(value = "/inquiry")
    public ResponseData accountInquiry(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            int mainAccountId = jsonObject.getInt("mainAccountID");
            if (mainAccountId != 0) {
                log.info("main account id:"+mainAccountId);
                JsonObject inquirySubAccount = new JsonObject();
                inquirySubAccount.setInt("mainAccountID", mainAccountId);
                JsonObjectArray subAccounts = accountService.inquirySubAccount(inquirySubAccount);
                log.info("Sub Account : " + objectMapper.writeValueAsString(subAccounts));
                responseData.setBody(subAccounts);
            } else {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_MainAccountID");
            }
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        responseData.setResult(header);
        return responseData;
    }
}
