package org.nahajski.baseauth.converter;

import org.nahajski.baseauth.dto.UserDTO;
import org.nahajski.baseauth.entity.UserEntity;

public class UserConverter {
    public static UserDTO convert(UserEntity userEntity) {
        return UserDTO.builder().name(userEntity.getName()).email(userEntity.getEmail()).build();
    }
}
