package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;

public interface TransactionInfoInterface {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
    JsonObjectArray inquiryDepositTransactionHistory(JsonObject param) throws ValidatorException;
    JsonObjectArray inquiryWithdrawalCashOutTransactionHistory(JsonObject param) throws ValidatorException;
}
