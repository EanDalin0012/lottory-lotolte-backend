package com.lattory.lattoryLotoBackEnd.core.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.events.HistoryUserLoginEvent;
import com.lattory.lattoryLotoBackEnd.core.events.WingNotifyEvent;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.implement.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

@RestController
@RequestMapping(value = "/api/user/v0")
public class UserRest {
    private static final Logger log = LoggerFactory.getLogger(UserRest.class);
    final UserService userService;
    @Inject
    private ApplicationEventPublisher eventPublisher;

    UserRest(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        JsonObject output = new JsonObject();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            log.info(objectMapper.writeValueAsString(jsonObject));
            String userName = jsonObject.getString("userName");
            String networkID = jsonObject.getString("networkIP");
            JsonObject deviceInfo = jsonObject.getJsonObject("deviceInfo");
            deviceInfo.setString("userName", userName);
            if(deviceInfo != null) {
                eventPublisher.publishEvent(new HistoryUserLoginEvent(deviceInfo));
            }

            if(!userName.trim().equals("") || userName == null) {
                JsonObject user = new JsonObject();
                user.setString("userName", userName);
                JsonObject userData = userService.loadUserByName(user);
                log.info("User Info:"+objectMapper.writeValueAsString(userData));
                responseData.setBody(userData);
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


}
