package com.jointpurchases;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JointPurchaseController {

    @GetMapping("/")
    public String hello(){
        return "hello joint-purchase";
    }
}