package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface Base64WriteImageInterface {
    int save(JsonObject param) throws ValidatorException;
    int delete(JsonObject param) throws ValidatorException;
    int update(JsonObject param) throws ValidatorException;
    String getResourcesImageById(JsonObject param) throws ValidatorException;
}
