package com.lattory.lattoryLotoBackEnd.core.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface DeviceInfoInterface {
    int save(JsonObject param);
    int updateDeviceInfo(JsonObject param);
    JsonObjectArray inquiry(JsonObject param);
    JsonObjectArray inquiryByUserAgent(JsonObject param) throws ValidatorException;
    int count();
    int deleteDeviceInfo(JsonObject jsonObject) throws ValidatorException;

}
