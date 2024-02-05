package com.jointpurchases.domain.security.contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PublicController {
    @GetMapping
    public String publicHello() {
        return "Hello World! This is the public endpoint!";
    }
}