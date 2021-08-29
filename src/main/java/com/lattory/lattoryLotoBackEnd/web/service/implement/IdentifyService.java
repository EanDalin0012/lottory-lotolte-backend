package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.web.dao.IdentifyDao;
import com.lattory.lattoryLotoBackEnd.web.service.IdentifyInterface;
import org.springframework.stereotype.Service;

@Service
public class IdentifyService implements IdentifyInterface {
    final IdentifyDao identifyDao;
    IdentifyService(IdentifyDao identifyDao) {
        this.identifyDao = identifyDao;
    }
    @Override
    public int save(JsonObject jsonObject) {
        return this.identifyDao.save(jsonObject);
    }

    @Override
    public int count() {
        return this.identifyDao.count();
    }
}
