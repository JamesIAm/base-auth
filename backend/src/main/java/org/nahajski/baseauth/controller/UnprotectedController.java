package org.nahajski.baseauth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnprotectedController {
    @GetMapping("/unprotected")
    private String sayHello() {
        return "Hello from an unprotected endpoint";
    }
}
