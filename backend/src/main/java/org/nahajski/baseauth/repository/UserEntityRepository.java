package org.nahajski.baseauth.repository;

import org.nahajski.baseauth.entity.OAuthIssuerSubject;
import org.nahajski.baseauth.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserEntityRepository extends JpaRepository<UserEntity, OAuthIssuerSubject> {
}
