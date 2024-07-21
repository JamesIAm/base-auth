package org.nahajski.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UnprotectedController {
    @GetMapping("/public")
    private String sayHello() {
        return "Hello from an unprotected endpoint";
    }

    @PostMapping("/public/test")
    private String testingPublicPost() {
        System.out.println("Made a public post");
        return "";
    }
}
