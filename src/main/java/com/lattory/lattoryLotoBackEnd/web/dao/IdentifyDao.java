package com.lattory.lattoryLotoBackEnd.web.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IdentifyDao {
    int save(JsonObject jsonObject);
    int count();
}
