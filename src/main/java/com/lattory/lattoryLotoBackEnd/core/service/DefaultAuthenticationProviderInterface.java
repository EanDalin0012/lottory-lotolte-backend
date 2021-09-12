package com.lattory.lattoryLotoBackEnd.core.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;

public interface DefaultAuthenticationProviderInterface {
    JsonObject getUserObjectByName(JsonObject param) throws Exception;
    JsonObject authenticate(JsonObject jsonObject);
}
