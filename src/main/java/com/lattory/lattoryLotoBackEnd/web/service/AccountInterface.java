package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface AccountInterface {
    JsonObject save(JsonObject param) throws ValidatorException;
    JsonObjectArray inquirySubAccount(JsonObject param) throws ValidatorException;
    JsonObject inquiryAccountByUserID(JsonObject param) throws ValidatorException;
}