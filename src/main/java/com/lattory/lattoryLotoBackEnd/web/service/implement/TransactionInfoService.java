package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.TransactionInfoDao;
import com.lattory.lattoryLotoBackEnd.web.service.TransactionInfoInterface;
import org.springframework.stereotype.Service;

@Service
public class TransactionInfoService implements TransactionInfoInterface {
    final TransactionInfoDao transactionInfoDao;

    TransactionInfoService(TransactionInfoDao transactionInfoDao) {
        this.transactionInfoDao = transactionInfoDao;
    }

    @Override
    public int count() {
        return this.transactionInfoDao.count();
    }

    @Override
    public int doTransaction(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "fromAccountIDReference", "fromAccountID","toAccountIDReference","toAccountID","transactionType", "transactionAmount", "currency", "userID");
        return this.transactionInfoDao.doTransaction(param);
    }

    @Override
    public JsonObjectArray inquiryDepositTransactionHistory(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountID", "transactionType");
        return this.transactionInfoDao.inquiryDepositTransactionHistory(param);
    }

    @Override
    public JsonObjectArray inquiryWithdrawalCashOutTransactionHistory(JsonObject param) throws ValidatorException {
        ValidatorUtil.validate(param, "id", "accountID", "transactionType");
        return this.transactionInfoDao.inquiryWithdrawalCashOutTransactionHistory(param);
    }

//    @Override
//    public JsonObjectArray inquiryDoTransactionDepositHistory(JsonObject param) throws ValidatorException {
//        ValidatorUtil.validate(param, "id", "accountID", "transactionType");
//        return this.transactionInfoDao.inquiryDoTransactionHistory(param);
//    }
}
