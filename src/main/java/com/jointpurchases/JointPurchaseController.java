package com.jointpurchases;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JointPurchaseController {

    @GetMapping("/")
    public String hello(){
        return "배포 테스트";
    }
}