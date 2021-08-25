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
    public JsonObject save(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountName", "accountID","accountType","accountStatus","userID");
        return accountDao.save(param);
    }

    @Override
    public JsonObjectArray inquirySubAccount(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "mainAccountID");
        return accountDao.inquirySubAccount(param);
    }

    @Override
    public JsonObject inquiryAccountByUserID(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "userID");
        return accountDao.inquiryAccountByUserID(param);
    }
}
