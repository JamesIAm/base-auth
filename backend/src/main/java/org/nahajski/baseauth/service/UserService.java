package org.nahajski.baseauth.service;

import org.nahajski.baseauth.entity.UserEntity;
import org.nahajski.baseauth.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserEntityRepository userEntityRepository;

    public UserService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    public void saveUser(UserEntity userEntity) {
        userEntityRepository.save(userEntity);
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userEntityRepository.findByEmail(email);
    }
}
