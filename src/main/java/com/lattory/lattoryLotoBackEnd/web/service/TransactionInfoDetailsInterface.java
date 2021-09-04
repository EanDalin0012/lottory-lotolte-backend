package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface TransactionInfoDetailsInterface {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
}
