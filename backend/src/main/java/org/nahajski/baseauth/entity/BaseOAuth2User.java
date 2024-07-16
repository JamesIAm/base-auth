package org.nahajski.baseauth.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

public class BaseOAuth2User extends DefaultOAuth2User {
    private final UserEntity user;
    public BaseOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, UserEntity user) {
        super(authorities, attributes, nameAttributeKey);
        this.user = user;
    }

    public UserEntity getUser() {
        return user;
    }
}
