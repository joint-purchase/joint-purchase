package com.jointpurchases.domain.security.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {

    @GetMapping
    public String adminHello() {
        return "Hello World! This is the admin endpoint!";
    }
}
