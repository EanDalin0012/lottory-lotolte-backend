package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountDepositDetailService;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountDepositService;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account-deposit")
public class AccountDepositRest {
    private static final Logger log = LoggerFactory.getLogger(AccountDepositRest.class);
    final PlatformTransactionManager transactionManager;
    final AccountService accountService;
    final AccountDepositService accountDepositService;
    final AccountDepositDetailService accountDepositDetailService;

    AccountDepositRest(PlatformTransactionManager platformTransactionManager, AccountService accountService, AccountDepositService accountDepositService, AccountDepositDetailService accountDepositDetailService) {
        this.transactionManager = platformTransactionManager;
        this.accountService = accountService;
        this.accountDepositService = accountDepositService;
        this.accountDepositDetailService = accountDepositDetailService;
    }

    @PostMapping(value = "/v0/save")
    public ResponseData save(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        ObjectMapper objectMapper = new ObjectMapper();
        Header header = new Header(StatusCode.notFound, MessageCode.success);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            String accountID    = jsonObject.getString("yourAccountID");
            int accountId       = jsonObject.getInt("yourAccountId");
            String toAccountID  = jsonObject.getString("toAccountID");
            int toAccountId     = jsonObject.getInt("toAccountId");
            int amount          = jsonObject.getInt("amount");
            String remark       = jsonObject.getString("remark");
            String currency     = jsonObject.getString("currency");

            if (accountId <= 0) {
                header.setResponseMessage("Require_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (accountID ==null || accountID == "") {
                header.setResponseMessage("Require_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (toAccountID ==null || toAccountID == "") {
                header.setResponseMessage("Require_ToAccountID");
                responseData.setResult(header);
                return responseData;
            } else if (toAccountId <= 0) {
                header.setResponseMessage("Require_ToAccountID");
                responseData.setResult(header);
                return responseData;
            } else if (amount <= 0) {
                header.setResponseMessage("Require_Amount");
                responseData.setResult(header);
                return responseData;
            } else if ( currency ==null || currency == "" ) {
                header.setResponseMessage("Require_Currency");
                responseData.setResult(header);
                return responseData;
            } else {
                JsonObject accountInfoInput = new JsonObject();
                accountInfoInput.setInt("id", accountId);
                JsonObject accountInfo = accountService.inquiryAccountByID(accountInfoInput);

                JsonObject toAccountInfoInput = new JsonObject();
                accountInfoInput.setInt("id", toAccountId);
                JsonObject toAccountInfo = accountService.inquiryAccountByID(toAccountInfoInput);


                JsonObject accountDeposit = new JsonObject();
                int accountDepositId  = this.accountDepositService.count() + 1;
                accountDeposit.setInt("id", accountDepositId);
                accountDeposit.setString("accountID", accountID);
                accountDeposit.setInt("accountId", accountId);
                accountDeposit.setString("toAccountID", toAccountID);
                accountDeposit.setInt("toAccountId", toAccountId);
                accountDeposit.setInt("amount", amount);
                accountDeposit.setString("currency", currency);
                accountDeposit.setString("remark", remark);
                accountDeposit.setInt("userID", userID);
                int saveAccountDeposit = this.accountDepositService.save(accountDeposit);


                JsonObject accountDepositDetail = new JsonObject();
                int accountDepositDetailId = this.accountDepositDetailService.count() + 1;
                accountDepositDetail.setInt("accountId",accountInfo.getInt("id"));
                accountDepositDetail.setString("accountID", accountInfo.getString("accountID"));
                accountDepositDetail.setInt("currentAccountBalance", accountInfo.getInt("accountBalance"));
                accountDepositDetail.setInt("toAccountId", toAccountInfo.getInt("id"));
                accountDepositDetail.setString("toAccountID", toAccountInfo.getString("accountID"));
                accountDepositDetail.setInt("toCurrentAccountBalance", toAccountInfo.getInt("accountBalance"));
                accountDepositDetail.setInt("amount", amount);
                accountDepositDetail.setString("currency", currency);
                accountDepositDetail.setInt("userID", userID);
                int saveAccountDepositDetail = this.accountDepositDetailService.save(accountDepositDetail);


                JsonObject updateYourAccountBalance = new JsonObject();
                int currentBalanceYourAcc = accountInfo.getInt("accountBalance") - amount;
                updateYourAccountBalance.setInt("id", accountInfo.getInt("id"));
                updateYourAccountBalance.setString("accountID", accountInfo.getString("accountID"));
                updateYourAccountBalance.setInt("accountBalance", currentBalanceYourAcc);
                int updateYourAcc = this.accountService.updateAccountBalance(updateYourAccountBalance);

                JsonObject updateToAccountBalance = new JsonObject();
                int currentBalanceToAcc = toAccountInfo.getInt("accountBalance") + amount;
                updateToAccountBalance.setInt("id", toAccountInfo.getInt("id"));
                updateToAccountBalance.setString("accountID", toAccountInfo.getString("accountID"));
                updateToAccountBalance.setInt("accountBalance", currentBalanceToAcc);
                int updateToAcc = this.accountService.updateAccountBalance(updateToAccountBalance);

                if (updateYourAcc > 0 && updateToAcc > 0 && saveAccountDeposit > 0 && saveAccountDepositDetail > 0 ) {
                    // success
                }
            }

        }catch (Exception | ValidatorException e) {
            log.error(String.valueOf(e));
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            transactionManager.rollback(transactionStatus);
            return responseData;
        }

        return responseData;
    }

}
