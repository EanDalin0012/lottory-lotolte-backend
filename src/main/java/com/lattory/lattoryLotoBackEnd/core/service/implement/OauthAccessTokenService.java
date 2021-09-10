package com.lattory.lattoryLotoBackEnd.core.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dao.OauthAccessTokenDao;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.service.OauthAccessTokenInterface;
import org.springframework.stereotype.Service;

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
    public JsonObject getClientIDUserName(JsonObject jsonObject) {
        return this.oauthAccessTokenDao.getClientIDUserName(jsonObject);
    }

}
