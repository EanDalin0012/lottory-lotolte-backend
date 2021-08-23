package com.lattory.lattoryLotoBackEnd.core.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface UserInterface {
    JsonObject loadUserByName(JsonObject param) throws ValidatorException;
}
