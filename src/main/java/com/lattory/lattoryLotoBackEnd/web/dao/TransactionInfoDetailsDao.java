package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TransactionInfoDetailsDao {
    int count();
    int doTransaction(JsonObject param) throws ValidatorException;
}
