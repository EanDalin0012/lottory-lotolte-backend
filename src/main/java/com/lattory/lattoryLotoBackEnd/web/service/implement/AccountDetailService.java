package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.AccountDetailDao;
import com.lattory.lattoryLotoBackEnd.web.service.AccountDetailInterface;
import org.springframework.stereotype.Service;

@Service
public class AccountDetailService implements AccountDetailInterface {
    final AccountDetailDao accountDetailDao;
    AccountDetailService(AccountDetailDao accountDetailDao) {
        this.accountDetailDao = accountDetailDao;
    }
    @Override
    public int addSubAccount(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "mainAccountID", "subAccountID");
        return this.accountDetailDao.addSubAccount(jsonObject);
    }

    @Override
    public int count() {
        return this.accountDetailDao.count();
    }
}
