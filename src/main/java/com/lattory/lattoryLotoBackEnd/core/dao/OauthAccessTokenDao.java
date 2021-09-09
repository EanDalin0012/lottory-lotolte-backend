package com.lattory.lattoryLotoBackEnd.core.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OauthAccessTokenDao {
    int deleteOauthAccessTokenByUserName(JsonObject jsonObject);
    JsonObject getTokenByUserName(JsonObject jsonObject);
}
