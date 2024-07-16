package org.nahajski.baseauth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_entity")
@Data

public class UserEntity {

    @EmbeddedId
    OauthIssuerSubject oauthId;

    String email;
    String name;

    @Column(name = "role")
    @Enumerated
    UserRole role;


}
