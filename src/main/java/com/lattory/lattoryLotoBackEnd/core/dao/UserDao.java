package com.lattory.lattoryLotoBackEnd.core.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {
    JsonObject loadUserByName(JsonObject param);
    int addNewUser(JsonObject jsonObject) throws ValidatorException;
    int resetPassword(JsonObject jsonObject) throws ValidatorException;
    int count();
    int updateUserInfo(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException;
}
