package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDetailDao {
    int addSubAccount(JsonObject jsonObject);
    int count();
}
