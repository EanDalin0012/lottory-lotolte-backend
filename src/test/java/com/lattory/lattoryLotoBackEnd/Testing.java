package com.lattory.lattoryLotoBackEnd;

import org.springframework.boot.SpringApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
        String password = "admin123";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptPassword = passwordEncoder.encode(password);
        System.out.println("encryptPassword:"+encryptPassword);

    }
}
