package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.AccountDepositDetailDao;
import com.lattory.lattoryLotoBackEnd.web.service.AccountDepositDetailInterface;
import org.springframework.stereotype.Service;

@Service
public class AccountDepositDetailService implements AccountDepositDetailInterface {
    final AccountDepositDetailDao accountDepositDetailDao;
    AccountDepositDetailService(AccountDepositDetailDao accountDepositDetailDao) {
        this.accountDepositDetailDao = accountDepositDetailDao;
    }
    @Override
    public int count() {
        return this.accountDepositDetailDao.count();
    }

    @Override
    public int save(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "depositId", "accountId","currentAccountBalance","toAccountId","toCurrentAccountBalance", "currency", "amount", "userID");
        return this.accountDepositDetailDao.save(param);
    }
}
