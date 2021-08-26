package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.AccountDao;
import com.lattory.lattoryLotoBackEnd.web.service.AccountInterface;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountInterface {
    final AccountDao accountDao;

    AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public int save(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountName", "accountID","accountType","accountStatus","userID");
        return this.accountDao.save(param);
    }

    @Override
    public JsonObjectArray inquirySubAccount(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "mainAccountID");
        return this.accountDao.inquirySubAccount(param);
    }

    @Override
    public JsonObject inquiryAccountByUserID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userID");
        return this.accountDao.inquiryAccountByUserID(param);
    }

    @Override
    public int count() {
        return this.accountDao.count();
    }
}
