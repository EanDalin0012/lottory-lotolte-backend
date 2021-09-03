package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface AccountDepositDetailInterface {
    int count();
    int save(JsonObject param) throws ValidatorException;
}
