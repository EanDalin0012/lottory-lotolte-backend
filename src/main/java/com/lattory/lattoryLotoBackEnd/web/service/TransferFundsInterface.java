package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface TransferFundsInterface {
    JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject) throws ValidatorException;
    int withdrawalTransferFunds(JsonObject jsonObject) throws ValidatorException;
    int depositTransferFunds(JsonObject jsonObject) throws ValidatorException;
}
