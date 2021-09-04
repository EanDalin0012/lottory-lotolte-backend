package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Service;

@Mapper
public interface TransactionInfoDao {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
    JsonObjectArray inquiryDepositTransactionHistory(JsonObject param) throws ValidatorException;
    JsonObjectArray inquiryWithdrawalCashOutTransactionHistory(JsonObject param) throws ValidatorException;
}
