package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IsOnlineUserDao {
    int updateUserToOffline(JsonObject object);
    int updateUserToOnline(JsonObject object);
    JsonObject isOnlineUser(JsonObject object);
}
