package com.humga.moneytransferservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.humga.moneytransferservice"})
public class MoneyTransferServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferServiceApplication.class, args);
    }

}
