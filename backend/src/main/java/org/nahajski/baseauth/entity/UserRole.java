package org.nahajski.baseauth.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    USER, ADMIN;

    private final String authority;
    UserRole() {
        this.authority = "ROLE_" + this;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}