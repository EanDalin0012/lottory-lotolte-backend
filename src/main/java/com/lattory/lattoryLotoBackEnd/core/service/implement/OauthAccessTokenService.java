package com.lattory.lattoryLotoBackEnd.core.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dao.OauthAccessTokenDao;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.OauthAccessTokenInterface;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;

@Service
public class OauthAccessTokenService implements OauthAccessTokenInterface {
    final OauthAccessTokenDao oauthAccessTokenDao;
    OauthAccessTokenService(OauthAccessTokenDao oauthAccessTokenDao) {
        this.oauthAccessTokenDao = oauthAccessTokenDao;
    }
    @Override
    public int deleteOauthAccessTokenByUserName(JsonObject jsonObject) {
        return this.oauthAccessTokenDao.deleteOauthAccessTokenByUserName(jsonObject);
    }

    @Override
    public JsonObject getTokenByUserName(JsonObject jsonObject) {
        return null;
    }
}
