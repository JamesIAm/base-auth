package org.nahajski.baseauth.cotroller;

import org.nahajski.baseauth.converter.UserConverter;
import org.nahajski.baseauth.dto.UserDTO;
import org.nahajski.baseauth.entity.BaseOAuth2User;
import org.nahajski.baseauth.entity.UserEntity;
import org.nahajski.baseauth.repository.UserEntityRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    public UserController() {
    }

    @GetMapping("/public/userinfo")
    public UserDTO getUserInfo(@AuthenticationPrincipal BaseOAuth2User oAuth2User) {
        if (oAuth2User == null) {
            return null;
        }
        return UserConverter.convert(oAuth2User.getUser());
    }
}
