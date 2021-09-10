package com.lattory.lattoryLotoBackEnd.core.events.listeners;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.events.HistoryUserLoginEvent;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.implement.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class HistoryUserLoginEventListener implements ApplicationListener<HistoryUserLoginEvent> {
    private static final Logger log = LoggerFactory.getLogger(HistoryUserLoginEventListener.class);
    final DeviceInfoService deviceInfoService;

    HistoryUserLoginEventListener(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    @Override
    public void onApplicationEvent(HistoryUserLoginEvent event) {
        try {
            ObjectMapper objectMapper =new ObjectMapper();
            JsonObject deviceInfo = (JsonObject) event.getSource();
            JsonObject input = new JsonObject();
            input.setInt("id", (deviceInfoService.count() + 1));
            input.setString("userAgent", deviceInfo.getString("userAgent"));
            input.setString("os", deviceInfo.getString("os"));
            input.setString("device", deviceInfo.getString("device"));
            input.setString("browser", deviceInfo.getString("browser"));
            input.setString("osVersion", deviceInfo.getString("osVersion"));
            input.setString("deviceType", deviceInfo.getString("deviceType"));
            input.setString("browserVersion", deviceInfo.getString("browserVersion"));
            input.setString("orientation", deviceInfo.getString("orientation"));
            input.setString("networkIP", deviceInfo.getString("networkIP"));
            input.setInt("userID", deviceInfo.getInt("userID"));
            input.setString("date", deviceInfo.getString("date"));
            log.info("device info:"+objectMapper.writeValueAsString(input));

            JsonObjectArray jsonObjectArray = this.deviceInfoService.inquiryByUserAgent(input);
            int size = jsonObjectArray.size();
            if(size > 1) {
                deviceInfoService.deleteDeviceInfo(input);
                deviceInfoService.save(input);
            } else if (size == 1 ) {
                deviceInfoService.updateDeviceInfo(input);
            }else {
                deviceInfoService.save(input);
            }
        } catch (Exception | ValidatorException e) {
            e.printStackTrace();
        }
    }
}
