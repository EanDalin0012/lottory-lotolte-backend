package com.lattory.lattoryLotoBackEnd.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.events.HistoryUserLoginEvent;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.implement.UserService;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/user")
public class UserRest {
    private static final Logger log = LoggerFactory.getLogger(UserRest.class);
    final UserService userService;
    final AccountService accountService;
    @Inject
    private ApplicationEventPublisher eventPublisher;

    UserRest(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @PostMapping(value = "/v0/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang, @RequestParam("date") String date) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(jsonObject));
            String userName = jsonObject.getString("userName");
            JsonObject deviceInfo = jsonObject.getJsonObject("deviceInfo");

            if(!userName.trim().equals("") || userName == null) {
                JsonObject user = new JsonObject();
                user.setString("userName", userName);
                JsonObject userData = this.userService.loadUserByName(user);
                log.info("User Info :"+objectMapper.writeValueAsString(userData));
                JsonObject accountInput = new JsonObject();
                accountInput.setInt("userID", userData.getInt("id"));
                JsonObject accountInfo = this.accountService.inquiryAccountByUserID(accountInput);

                log.info("account Info:"+objectMapper.writeValueAsString(accountInfo));

//                if(deviceInfo != null) {
//                    deviceInfo.setString("date", date);
//                    deviceInfo.setInt("userID", userData.getInt("id"));
//                    eventPublisher.publishEvent(new HistoryUserLoginEvent(deviceInfo));
//                }

                JsonObject data = new JsonObject();
                data.setJsonObject("userInfo", userData);
                data.setJsonObject("accountInfo", accountInfo);

                responseData.setBody(data);

            } else {
                header.setResponseCode(StatusCode.notFound);
                header.setResponseMessage("Invalid_UserName");

            }
            responseData.setResult(header);
            return responseData;
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(value = "/v0/update/info")
    public ResponseData<JsonObject> updateUserInfo(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String firstName =  jsonObject.getString("firstName").trim();
            String lastName =  jsonObject.getString("lastName").trim();
            String gender = jsonObject.getString("gender").trim();
            String dateBirth = jsonObject.getString("dateBirth").trim();

            if (firstName ==null || firstName == "") {
                header.setResponseMessage("Invalid_FirstName");
                responseData.setResult(header);
                return responseData;
            }  else if (lastName ==null || lastName.equals("")) {
                header.setResponseMessage("Invalid_LastName");
                responseData.setResult(header);
                return responseData;
            } else if (gender ==null || gender.equals("")) {
                header.setResponseMessage("Invalid_Gender");
                responseData.setResult(header);
                return responseData;
            } else if (dateBirth ==null || dateBirth.equals("")) {
                header.setResponseMessage("Invalid_DB");
                responseData.setResult(header);
                return responseData;
            } else {
                int update = this.userService.updateUserInfo(jsonObject);
                if (update > 0 ) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return  responseData;
                }
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        return  responseData;
    }


    @PostMapping(value = "/v0/reset-password")
    public ResponseData<JsonObject> resetPassword(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String userName =  jsonObject.getString("userName").trim();
            int id =  jsonObject.getInt("id");
            String password = jsonObject.getString("password").trim();

            if (id == 0) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            }  else if (userName ==null || userName.equals("")) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            } else if (password ==null || password.equals("")) {
                header.setResponseMessage("Invalid_Password");
                responseData.setResult(header);
                return responseData;
            }  else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encryptPassword = passwordEncoder.encode(password);
                JsonObject input = new JsonObject();
                input.set("id", id);
                input.setString("userName", userName);
                input.setString("password", encryptPassword);
                input.setBoolean("isFirstLogin", true);
                int update = this.userService.resetPassword(input);
                if (update > 0 ) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return  responseData;
                }
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        return  responseData;
    }

    @PostMapping(value = "/v0/change-password")
    public ResponseData<JsonObject> changePassword(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            String userName =  jsonObject.getString("userName").trim();
            int id =  jsonObject.getInt("id");
            String password = jsonObject.getString("password").trim();

            if (id == 0) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            }  else if (userName ==null || userName.equals("")) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            } else if (password ==null || password.equals("")) {
                header.setResponseMessage("Invalid_Password");
                responseData.setResult(header);
                return responseData;
            }  else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String encryptPassword = passwordEncoder.encode(password);
                JsonObject input = new JsonObject();
                input.set("id", id);
                input.setString("userName", userName);
                input.setString("password", encryptPassword);
                input.setBoolean("isFirstLogin", false);
                int update = this.userService.resetPassword(input);
                if (update > 0 ) {
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return  responseData;
                }
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            return responseData;
        }
        return  responseData;
    }



}
