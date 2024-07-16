package org.nahajski.baseauth.security;

import jakarta.servlet.http.Cookie;

public class LoggedInCookie extends Cookie {
    public LoggedInCookie(boolean loggedIn) {

        super("logged-in", String.valueOf(loggedIn));
        super.setMaxAge(86400);
        super.setDomain("localhost");
        super.setPath("/");
    }
}
