package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface AccountDetailInterface {
    int addSubAccount(JsonObject jsonObject) throws ValidatorException;
    int count();
}
