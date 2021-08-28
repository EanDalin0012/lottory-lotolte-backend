package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.IsOnlineUserDao;
import com.lattory.lattoryLotoBackEnd.web.service.IsOnlineUserInterface;
import org.springframework.stereotype.Service;

@Service
public class IsOnlineUserService implements IsOnlineUserInterface {

    final IsOnlineUserDao isOnlineUserDao;
    IsOnlineUserService(IsOnlineUserDao isOnlineUserDao) {
        this.isOnlineUserDao = isOnlineUserDao;
    }
    @Override
    public int updateUserToOffline(JsonObject object) throws ValidatorException {
        ValidatorUtil.validate(object, "userID", "isOnline");
        return this.isOnlineUserDao.updateUserToOffline(object);
    }

    @Override
    public int updateUserToOnline(JsonObject object) throws ValidatorException {
        ValidatorUtil.validate(object, "userID", "isOnline");
        return this.isOnlineUserDao.updateUserToOnline(object);
    }

    @Override
    public JsonObject isOnlineUser(JsonObject object) throws ValidatorException {
        ValidatorUtil.validate(object, "userID");
        return this.isOnlineUser(object);
    }
}
