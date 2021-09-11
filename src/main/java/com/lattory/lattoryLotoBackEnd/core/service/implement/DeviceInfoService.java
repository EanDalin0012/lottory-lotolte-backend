package com.lattory.lattoryLotoBackEnd.core.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dao.DeviceInfoDao;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.DeviceInfoInterface;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoService implements DeviceInfoInterface {
    final DeviceInfoDao deviceInfoDao;

    DeviceInfoService(DeviceInfoDao deviceInfoDao) {
        this.deviceInfoDao = deviceInfoDao;
    }
    @Override
    public int save(JsonObject param) {
        return this.deviceInfoDao.save(param);
    }

    @Override
    public int updateDeviceInfo(JsonObject param) {
        return this.deviceInfoDao.updateDeviceInfo(param);
    }

    @Override
    public JsonObjectArray inquiry(JsonObject param) {
        return this.deviceInfoDao.inquiry(param);
    }

    @Override
    public JsonObjectArray inquiryByUserAgent(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userName", "userAgent");
        return this.deviceInfoDao.inquiryByUserAgent(param);
    }

    @Override
    public int count() {
        return this.deviceInfoDao.count();
    }

    @Override
    public int deleteDeviceInfo(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userName", "userAgent");
        return this.deviceInfoDao.deleteDeviceInfo(jsonObject);
    }
}
