package com.lattory.lattoryLotoBackEnd.web.service.implement;

import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObjectArray;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.util.ValidatorUtil;
import com.lattory.lattoryLotoBackEnd.web.dao.TransferFundsDao;
import com.lattory.lattoryLotoBackEnd.web.service.CountInterface;
import com.lattory.lattoryLotoBackEnd.web.service.TransferFundsInterface;
import org.springframework.stereotype.Service;

@Service
public class TransferFundsService implements TransferFundsInterface, CountInterface
{
    final TransferFundsDao transferFundsDao;
    TransferFundsService(TransferFundsDao transferFundsDao) {
        this.transferFundsDao = transferFundsDao;
    }
    @Override
    public JsonObjectArray inquiryHistoryTransferFundsByUser(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "userID");
        return this.transferFundsDao.inquiryHistoryTransferFundsByUser(jsonObject);
    }

    @Override
    public int withdrawalTransferFunds(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "transferFromAccountID", "receiverAccountID","amount","transferFundsType","currency","userID");
        return this.transferFundsDao.withdrawalTransferFunds(jsonObject);
    }

    @Override
    public int depositTransferFunds(JsonObject jsonObject) throws ValidatorException {
        ValidatorUtil.validate(jsonObject, "id", "transferFromAccountID", "receiverAccountID","amount","transferFundsType","currency","userID");
        return this.transferFundsDao.depositTransferFunds(jsonObject);
    }

    @Override
    public int count() {
        return this.transferFundsDao.count();
    }
}
