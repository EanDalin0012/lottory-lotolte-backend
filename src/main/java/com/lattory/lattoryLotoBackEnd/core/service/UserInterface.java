package com.lattory.lattoryLotoBackEnd.core.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface UserInterface {
    JsonObject loadUserByName(JsonObject param) throws ValidatorException;
    int addNewUser(JsonObject jsonObject) throws ValidatorException;
    int resetPassword(JsonObject jsonObject) throws ValidatorException;
    int count();
    int updateUserInfo(JsonObject jsonObject) throws ValidatorException;
    JsonObject inquiryUserInfoByID(JsonObject param) throws ValidatorException;
}
