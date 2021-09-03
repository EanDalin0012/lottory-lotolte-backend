package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDepositDetailDao {
    int count();
    int save(JsonObject param) throws ValidatorException;
}
