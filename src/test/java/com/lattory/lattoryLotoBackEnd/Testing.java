package com.lattory.lattoryLotoBackEnd;

import org.springframework.boot.SpringApplication;

public class Testing {
    public static void main(String[] args) {
        int accountID = 98;
        int accountLeng = String.valueOf(accountID).length();
        System.out.println(accountLeng);
        String account = "";
        for (int i = 0 ; i < 9 - accountLeng; i++) {
            account += "0";
        }
        System.out.println(account + accountID);
        System.out.println(account.length());
    }
}
