package org.nahajski.baseauth.entity;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
        USER, ADMIN;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}