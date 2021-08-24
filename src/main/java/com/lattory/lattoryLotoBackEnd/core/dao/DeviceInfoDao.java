package com.lattory.lattoryLotoBackEnd.core.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceInfoDao {
    JsonObject save(JsonObject param);
    JsonObjectArray inquiry(JsonObject param);
    int count();
}
