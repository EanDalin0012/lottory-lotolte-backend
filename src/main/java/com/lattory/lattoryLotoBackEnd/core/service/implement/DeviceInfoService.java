package com.lattory.lattoryLotoBackEnd.core.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dao.DeviceInfoDao;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.service.DeviceInfoInterface;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoService implements DeviceInfoInterface {
    final DeviceInfoDao deviceInfoDao;

    DeviceInfoService(DeviceInfoDao deviceInfoDao) {
        this.deviceInfoDao = deviceInfoDao;
    }
    @Override
    public JsonObject save(JsonObject param) {
        return deviceInfoDao.save(param);
    }

    @Override
    public JsonObjectArray inquiry() {
        return deviceInfoDao.inquiry();
    }

    @Override
    public int count() {
        return deviceInfoDao.count();
    }
}
