package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;

public interface IdentifyInterface {
    int save(JsonObject jsonObject);
    int count();
}
