package com.lattory.lattoryLotoBackEnd.core.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;

public interface DeviceInfoInterface {
    JsonObject save(JsonObject param);
    JsonObjectArray inquiry(JsonObject param);
    int count();
}
