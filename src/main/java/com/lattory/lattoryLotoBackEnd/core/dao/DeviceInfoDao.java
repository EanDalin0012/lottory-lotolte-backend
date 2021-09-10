package com.lattory.lattoryLotoBackEnd.core.dao;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeviceInfoDao {
    int save(JsonObject param);
    int updateDeviceInfo(JsonObject param);
    JsonObjectArray inquiry(JsonObject param);
    JsonObjectArray inquiryByUserAgent(JsonObject param);
    int count();
    int deleteDeviceInfo(JsonObject jsonObject);
}
