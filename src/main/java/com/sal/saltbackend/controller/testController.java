package com.sal.saltbackend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testController {

    @GetMapping("/nowij")
    public String Nowij() {
        System.out.println("123");
        return "Spring Boot and React 연동 테스트 \n";
    }
}