package org.nahajski.example.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {
    @GetMapping("/protected")
    private String sayHello() {
        return "Hello from a protected endpoint";
    }
}
