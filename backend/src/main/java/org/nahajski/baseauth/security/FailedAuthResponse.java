package org.nahajski.baseauth.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

@Slf4j
public class FailedAuthResponse implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.addCookie(new LoggedInCookie(false));
        log.debug("User tried to access a resource while unauthenticated, clearing logged in cookie and sending Access Denied");
        response.sendError(403, "Access Denied");
    }
}
