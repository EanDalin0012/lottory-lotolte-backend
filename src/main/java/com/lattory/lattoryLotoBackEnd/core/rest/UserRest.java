package com.lattory.lattoryLotoBackEnd.core.rest;

import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/user/v0")
public class UserRest {
    private static final Logger log = LoggerFactory.getLogger(UserRest.class);

    @PostMapping(value = "/loadUser")
    public ResponseData<JsonObject> loadUserByUserName(@RequestBody JsonObject jsonObject, @RequestParam("userID") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        JsonObject output = new JsonObject();
        try {
            String userName = jsonObject.getString("userName");
            String networkID = jsonObject.getString("networkIP");
            JsonObject deviceInfo = jsonObject.getJsonObject("deviceInfo");

            if (userName.trim().equals("") || userName == null) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
