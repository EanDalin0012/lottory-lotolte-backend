package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface AccountInterface {
    int save(JsonObject param) throws ValidatorException;
    JsonObjectArray inquirySubAccount(JsonObject param) throws ValidatorException;
    JsonObject inquiryAccountByUserID(JsonObject param) throws ValidatorException;
    int count();
    int maxAccountID();
    int updateAccountName(JsonObject param) throws ValidatorException;
    JsonObject inquiryUserInfoByAccountID(JsonObject param) throws ValidatorException;
    int disableAccount(JsonObject param) throws ValidatorException;
}
