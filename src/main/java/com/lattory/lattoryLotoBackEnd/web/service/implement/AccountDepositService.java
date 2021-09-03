package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.AccountDepositDao;
import com.lattory.lattoryLotoBackEnd.web.service.AccountDepositInterface;
import org.springframework.stereotype.Service;

@Service
public class AccountDepositService implements AccountDepositInterface {
    final AccountDepositDao accountDepositDao;
    AccountDepositService(AccountDepositDao accountDepositDao) {
        this.accountDepositDao = accountDepositDao;
    }
    @Override
    public int count() {
        return this.accountDepositDao.count();
    }

    @Override
    public int save(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountIDRefer", "accountID","toAccountID","toAccountIDRefer","userID", "currency", "amount");
        return this.accountDepositDao.save(param);
    }
}
