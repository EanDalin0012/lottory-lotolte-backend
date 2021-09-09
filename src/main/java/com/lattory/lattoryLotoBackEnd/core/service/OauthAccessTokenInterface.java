package com.lattory.lattoryLotoBackEnd.core.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface OauthAccessTokenInterface {
    int deleteOauthAccessTokenByUserName(JsonObject jsonObject) throws ValidatorException;
    JsonObject getTokenByUserName(JsonObject jsonObject);
}
