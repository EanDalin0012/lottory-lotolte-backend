package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.TransactionInfoDetailsDao;
import com.lattory.lattoryLotoBackEnd.web.service.TransactionInfoDetailsInterface;
import org.springframework.stereotype.Service;

@Service
public class TransactionInfoDetailsService implements TransactionInfoDetailsInterface {
    final TransactionInfoDetailsDao transactionInfoDetailsDao;
    TransactionInfoDetailsService(TransactionInfoDetailsDao transactionInfoDetailsDao) {
        this.transactionInfoDetailsDao = transactionInfoDetailsDao;
    }
    @Override
    public int count() {
        return this.transactionInfoDetailsDao.count();
    }

    @Override
    public int doTransaction(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "transactionInfoId", "fromAccountIDReference", "fromAccountBalance", "toAccountIDReference","toAccountBalance", "transactionType", "transactionAmount", "currency", "userID");
        return this.transactionInfoDetailsDao.doTransaction(param);
    }
}
