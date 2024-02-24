package com.jointpurchases;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JointPurchasesApplication {

    public static void main(String[] args) {
        SpringApplication.run(JointPurchasesApplication.class, args);
    }

}
