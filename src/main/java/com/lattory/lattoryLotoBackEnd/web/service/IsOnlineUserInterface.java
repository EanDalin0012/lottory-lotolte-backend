package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface IsOnlineUserInterface {
    int updateUserToOffline(JsonObject object) throws ValidatorException;
    int updateUserToOnline(JsonObject object) throws ValidatorException;
    JsonObject isOnlineUser(JsonObject object) throws ValidatorException;

}
