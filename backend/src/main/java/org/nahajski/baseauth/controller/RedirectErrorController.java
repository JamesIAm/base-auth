package org.nahajski.baseauth.controller;

import com.sun.net.httpserver.Headers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectErrorController implements ErrorController {
    private final String frontendUrl;

    public RedirectErrorController(@Value("${spring.security.oauth2.client.frontend.url}") String frontendUrl) {
        this.frontendUrl = frontendUrl;
    }

    @RequestMapping("/error")
    public ResponseEntity<String> catchAll() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", frontendUrl);
        headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");
        return new ResponseEntity<>("Hit an unexpected failure", headers, HttpStatus.SEE_OTHER );
    }
}