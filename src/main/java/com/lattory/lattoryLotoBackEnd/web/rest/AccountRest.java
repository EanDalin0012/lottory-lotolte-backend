package com.lattory.lattoryLotoBackEnd.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lattory.lattoryLotoBackEnd.core.constant.MessageCode;
import com.lattory.lattoryLotoBackEnd.core.constant.Status;
import com.lattory.lattoryLotoBackEnd.core.constant.StatusCode;
import com.lattory.lattoryLotoBackEnd.core.dto.Header;
import com.lattory.lattoryLotoBackEnd.core.dto.JsonObject;
import com.lattory.lattoryLotoBackEnd.core.dto.ResponseData;
import com.lattory.lattoryLotoBackEnd.core.exception.ValidatorException;
import com.lattory.lattoryLotoBackEnd.core.service.implement.UserService;
import com.lattory.lattoryLotoBackEnd.web.constant.AccountStatus;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountDetailService;
import com.lattory.lattoryLotoBackEnd.web.service.implement.AccountService;
import com.lattory.lattoryLotoBackEnd.web.service.implement.IdentifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account")
public class AccountRest {
    private static final Logger log = LoggerFactory.getLogger(AccountRest.class);

    final AccountService accountService;
    final UserService userService;
    final PlatformTransactionManager transactionManager;
    final AccountDetailService accountDetailService;
    final IdentifyService identifyService;

    AccountRest(AccountService accountService, UserService userService, AccountDetailService accountDetailService, PlatformTransactionManager platformTransactionManager, IdentifyService identifyService) {
        this.userService = userService;
        this.accountService = accountService;
        this.transactionManager = platformTransactionManager;
        this.accountDetailService = accountDetailService;
        this.identifyService = identifyService;
    }

    @PostMapping(value = "/v0/save")
    public ResponseData save(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("date") String date) {
        ResponseData responseData = new ResponseData();
        ObjectMapper objectMapper = new ObjectMapper();
        Header header = new Header(StatusCode.notFound, MessageCode.success);
        TransactionStatus transactionStatus = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            log.info(objectMapper.writeValueAsString(jsonObject));

            // Personal Information
            JsonObject personalAccountInfo = jsonObject.getJsonObject("personalInformation");
            JsonObject identifyInformation = jsonObject.getJsonObject("identifyInformation");

            JsonObject personalInform = new JsonObject();

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String passwd = personalAccountInfo.getString("password").trim();
            String password = passwordEncoder.encode(passwd);

            int mainAccountID = personalAccountInfo.getInt("mainAccountID");

            // validation
            String firstName =  personalAccountInfo.getString("firstName").trim();
            String lastName =  personalAccountInfo.getString("lastName").trim();
            String gender = personalAccountInfo.getString("gender").trim();
            String dateBirth = personalAccountInfo.getString("dateBirth").trim();
            String phoneNumber = personalAccountInfo.getString("phoneNumber").trim();
            String otherPhoneNumber = personalAccountInfo.getString("otherPhone").trim();
            String address = personalAccountInfo.getString("address").trim();

            int personalAccountInfoResourceID= personalAccountInfo.getInt("resourceID");
            String remark = jsonObject.getString("remark").trim();
            String userName = personalAccountInfo.getString("userName");
            String accountType = personalAccountInfo.getString("accountType").trim();
            String  currency = personalAccountInfo.getString("currency").trim();
            JsonObject checkUserNameInput = new JsonObject();
            checkUserNameInput.setString("userName", userName);
            JsonObject userInquiry = this.userService.loadUserByName(checkUserNameInput);
            if (userInquiry != null) {
                header.setResponseMessage("User_Had");
                responseData.setResult(header);
                return responseData;
            }
            if (firstName ==null || firstName == "") {
                header.setResponseMessage("Invalid_FirstName");
                responseData.setResult(header);
                return responseData;
            } else if (lastName ==null || lastName.equals("")) {
                header.setResponseMessage("Invalid_LastName");
                responseData.setResult(header);
                return responseData;
            } else if (passwd ==null || passwd.equals("")) {
                header.setResponseMessage("Invalid_Password");
                responseData.setResult(header);
                return responseData;
            } else if (gender ==null || gender.equals("")) {
                header.setResponseMessage("Invalid_Gender");
                responseData.setResult(header);
                return responseData;
            } else if (dateBirth ==null || dateBirth.equals("")) {
                header.setResponseMessage("Invalid_DB");
                responseData.setResult(header);
                return responseData;
            } else if (userName ==null || userName.equals("")) {
                header.setResponseMessage("Invalid_UserName");
                responseData.setResult(header);
                return responseData;
            }  else if (accountType ==null || accountType.equals("")) {
                header.setResponseMessage("Invalid_AccountType");
                responseData.setResult(header);
                return responseData;
            } else if (currency ==null || currency.equals("")) {
                header.setResponseMessage("Invalid_Currency");
                responseData.setResult(header);
                return responseData;
            } else if (mainAccountID <= 0) {
                header.setResponseMessage("Invalid_MainAccountID");
                responseData.setResult(header);
                return responseData;
            }

            // Identify Information
            int identifyInfoID = this.identifyService.count() + 1;
            String identifyID = identifyInformation.getString("identifyID").trim();

            if (identifyInformation != null && identifyID != "") {
                JsonObject identifyInfo = new JsonObject();
                identifyInfo.setInt("id", identifyInfoID);
                identifyInfo.setString("identifyID", identifyInformation.getString("identifyID"));
                identifyInfo.setInt("resourceID", identifyInformation.getInt("resourceID"));
                identifyInfo.setString("status", Status.active);
                identifyInfo.setInt("userID", userID);
                this.identifyService.save(identifyInfo);
                log.info("identifyInfo :"+ objectMapper.writeValueAsString(identifyInfo));
            }

            int personalInformID = this.userService.count() + 1;
            personalInform.setInt("id", personalInformID);
            personalInform.setString("userName", userName);
            personalInform.setString("password", password);
            personalInform.setString("firstName", firstName);
            personalInform.setString("lastName",lastName);
            personalInform.setString("gender", gender);
            personalInform.setString("dateBirth", dateBirth);
            personalInform.setString("phoneNumber", phoneNumber);
            personalInform.setString("otherPhoneNumber", otherPhoneNumber);
            personalInform.setInt("identifyID", identifyInfoID);
            personalInform.setInt("resourceID", personalAccountInfoResourceID);
            personalInform.setString("remark", remark);
            personalInform.setInt("userID", userID);
            personalInform.setString("address", address);
            personalInform.setString("date", date);

            log.info("personalInform :"+ objectMapper.writeValueAsString(personalInform));
            int savePersonalInfo = this.userService.addNewUser(personalInform);



            JsonObject accountInfo = new JsonObject();
            int accountInfoId = this.accountService.count() + 1;
            String accountID =  generateAccountID(this.accountService.maxAccountID() + 1);


            accountInfo.setInt("id", accountInfoId);
            accountInfo.setString("accountName", firstName + " "+lastName);
            accountInfo.setString("accountID", accountID);
            accountInfo.setString("accountType", accountType);
            accountInfo.setInt("accountBalance", 0);
            accountInfo.setString("accountStatus", AccountStatus.active);
            accountInfo.setInt("userID", personalInformID);
            accountInfo.setInt("createBy", userID);
            accountInfo.setString("currency", currency);
            accountInfo.setString("date", date);
            log.info("accountInfo :"+ objectMapper.writeValueAsString(accountInfo));
            int saveAccountInfo = this.accountService.save(accountInfo);

            JsonObject accountDetailsInfo = new JsonObject();
            int accountDetailsInfoID = this.accountDetailService.count() + 1;
            accountDetailsInfo.setInt("id", accountDetailsInfoID);
            accountDetailsInfo.setInt("mainAccountID", mainAccountID);
            accountDetailsInfo.setInt("subAccountID", accountInfoId);
            accountDetailsInfo.setInt("userID", userID);
            accountDetailsInfo.setString("date", date);

            int saveAccountDetails = this.accountDetailService.addSubAccount(accountDetailsInfo);

            JsonObject jsonObj = new JsonObject();

            if(savePersonalInfo > 0 && saveAccountInfo > 0 && saveAccountDetails > 0) {
                jsonObj.setString("status", "Y");
                jsonObj.setString("accountName", accountInfo.getString("accountName"));
                jsonObj.setString("accountID", accountID);
                jsonObj.setString("userName", personalInform.getString("userName"));
                jsonObj.setString("password", personalAccountInfo.getString("password"));
                transactionManager.commit(transactionStatus);
                responseData.setBody(jsonObj);
            } else {
                jsonObj.setString("status", "N");
                transactionManager.rollback(transactionStatus);
                responseData.setBody(jsonObj);
            }

        }catch (Exception | ValidatorException e) {
            e.printStackTrace();
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            transactionManager.rollback(transactionStatus);
            return responseData;
        }
        header.setResponseCode(StatusCode.success);
        responseData.setResult(header);
        return responseData;
    }

    @PostMapping(value = "/v0/update/accountName")
    public ResponseData updateAccountName(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.notFound, MessageCode.notFound);
        try {
            String accountName = jsonObject.getString("accountName");
            String accountID = jsonObject.getString("accountID");
            int id = jsonObject.getInt("id");
            if (id == 0) {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (accountName ==null || accountName == "") {
                header.setResponseMessage("Invalid_AccountName");
                responseData.setResult(header);
                return responseData;
            } else if (accountID ==null || accountID == "") {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else {
                int update = this.accountService.updateAccountName(jsonObject);
                if (update > 0) {
                    header.setResponseCode(StatusCode.success);
                    header.setResponseMessage( MessageCode.notFound);
                    responseData.setBody(header);
                    responseData.setResult(header);
                    return responseData;
                }
            }
        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }
        return responseData;
    }

    @PostMapping(value = "/v0/inquiry/user-info")
    public ResponseData inquiryUserByAccountID(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.success, MessageCode.success);
        try {
            JsonObject jsonObj = this.accountService.inquiryUserInfoByAccountID(jsonObject);

            JsonObject input = new JsonObject();
            input.setInt("userID", jsonObj.getInt("userID"));
            JsonObject userInfo = this.userService.inquiryUserInfoByID(input);
            responseData.setBody(userInfo);

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }

        responseData.setResult(header);
        return responseData;
    }


    @PostMapping(value = "/v0/disable-account")
    public ResponseData disableAccount(@RequestBody JsonObject jsonObject, @RequestParam("userId") int userID, @RequestParam("lang") String lang) {
        ResponseData responseData = new ResponseData();
        Header header = new Header(StatusCode.notFound, MessageCode.notFound);
        try {
            String accountID = jsonObject.getString("accountID");
            int id = jsonObject.getInt("id");
            String status = jsonObject.getString("status");

            if(id <= 0 ) {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (accountID ==null || accountID == "") {
                header.setResponseMessage("Invalid_AccountID");
                responseData.setResult(header);
                return responseData;
            } else if (status ==null || status == "") {
                header.setResponseMessage("Invalid_AccountStatus");
                responseData.setResult(header);
                return responseData;
            } else {
                JsonObject input = new JsonObject();
                input.setInt("userID", userID);
                input.setString("accountID", accountID);
                input.setString("status", status);
                input.setInt("id", id);
                input.setString("remark", jsonObject.getString("remark"));

                int update = this.accountService.disableAccount(input);
                if(update > 0) {
                    header.setResponseCode(StatusCode.success);
                    header.setResponseMessage(MessageCode.success);
                    responseData.setResult(header);
                    responseData.setBody(header);
                    return responseData;
                }
            }

        }catch (Exception | ValidatorException e) {
            header.setResponseCode(StatusCode.exception);
            header.setResponseMessage(StatusCode.exception);
            responseData.setResult(header);
            e.printStackTrace();
            return responseData;
        }

        responseData.setResult(header);
        return responseData;
    }


    private String generateAccountID(int accountID) {
        int accountLength = String.valueOf(accountID).length();

        System.out.println(accountLength);
        String account = "";
        for (int i = 0 ; i < 9 - accountLength; i++) {
            account += "0";
        }
        String accountId = account + accountID;
        if(accountLength == 9 && accountId == "999999999") {
            accountId = "1000000000";
        }
        System.out.println(accountId);
        System.out.println(accountId.length());
        return accountId;
    }
}
