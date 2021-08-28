package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransferFundsDao {
    JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject);
    int withdrawalTransferFunds(JsonObject jsonObject);
    int depositTransferFunds(JsonObject jsonObject);
    int count();
}
