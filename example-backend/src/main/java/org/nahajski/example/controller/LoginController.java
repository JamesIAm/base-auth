package org.nahajski.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    @GetMapping("/login")
    public ResponseEntity<?> login(ModelMap map) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
