package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.service.implement.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/my/account/v0")
public class MyAccountRest {
    private static final Logger log = LoggerFactory.getLogger(MyAccountRest.class);

    final DeviceInfoService deviceInfoService;
    MyAccountRest(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @PostMapping(value = "/inquiry")
    public ResponseData accountInquiry(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        JsonObject json = new JsonObject();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            log.info(""+objectMapper.writeValueAsString(jsonObject));
            JsonObject deviceInfo = new JsonObject();
            JsonObjectArray jsonObjectArray = deviceInfoService.inquiry(deviceInfo);
            json.setJsonObjectArray("deviceInfos",jsonObjectArray);
        }catch (Exception e) {
            e.printStackTrace();
        }
        responseData.setResult(header);
        responseData.setBody(json);
        return responseData;
    }
}
