package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountDao {
    int save(JsonObject param);
    JsonObjectArray inquirySubAccount(JsonObject param);
    JsonObject inquiryAccountByUserID(JsonObject param);
    int count();
    int maxAccountID();
}
