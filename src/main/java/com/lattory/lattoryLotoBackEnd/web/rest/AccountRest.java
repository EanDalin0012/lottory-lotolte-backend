package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/account/v0")
public class AccountRest {
    private static final Logger log = LoggerFactory.getLogger(AccountRest.class);

    final AccountService accountService;
    AccountRest(AccountService accountService) {
        this.accountService = accountService;
    }

    public ResponseData save(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        ObjectMapper objectMapper = new ObjectMapper();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try{
            log.info(objectMapper.writeValueAsString(objectMapper));

            // Personal Information
            JsonObject personalInformation = jsonObject.getJsonObject("personalInformation");
            JsonObject personalInform = new JsonObject();

            // User Information
            JsonObject userInformation = jsonObject.getJsonObject("userAccount");

            // Identify Information
            JsonObject identifyInformation = jsonObject.getJsonObject("identifyInformation");

            JsonObject jsonObj = new JsonObject();
            jsonObj.setInt("id", (this.accountService.count() + 1));
            int save = accountService.save(jsonObj);
        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }
        return responseData;
    }
}
