package org.nahajski.baseauth.controller;

import org.nahajski.baseauth.entity.BaseOAuth2User;
import org.nahajski.baseauth.entity.UserEntity;
import org.nahajski.baseauth.repository.UserEntityRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    private final UserEntityRepository userEntityRepository;

    public UserController(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @GetMapping("/users")
    public List<UserEntity> getAllUserEntities() {
        return userEntityRepository.findAll();
    }
    @GetMapping("/userinfo")
    public String getUserInfo(@AuthenticationPrincipal BaseOAuth2User oAuth2User) {
        return oAuth2User.getUser().getName() + ":" + oAuth2User.getUser().getEmail();
    }
}
