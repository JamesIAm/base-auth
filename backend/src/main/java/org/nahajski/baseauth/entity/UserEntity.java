package org.nahajski.baseauth.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Generated;
import org.springframework.data.jpa.repository.EntityGraph;

@Entity
@Table(name = "user_entity")
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String email;
    String name;

    @Column(name = "role")
    @Enumerated
    UserRole role;


}
