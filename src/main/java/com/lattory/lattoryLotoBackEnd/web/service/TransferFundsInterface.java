package com.lattory.lattoryLotoBackEnd.web.service;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;

public interface TransferFundsInterface {
    JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject);
    int withdrawalTransferFunds(JsonObject jsonObject);
    int depositTransferFunds(JsonObject jsonObject);
}
