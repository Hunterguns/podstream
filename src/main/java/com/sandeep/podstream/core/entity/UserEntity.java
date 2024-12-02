package com.sandeep.podstream.core.entity;

import com.sandeep.podstream.core.config.Encrypt;
import com.sandeep.podstream.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
import java.util.function.Function;


@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "user_table")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;
    @Column(name = "user_type")
    private String userType;
    @Column(name = "hashed_password")
    @Convert(converter = Encrypt.class)
    private String hashedPassword;

    public static final Function<UserEntity, User> toUser = userEntity -> User.builder()
            .username(userEntity.getUsername())
            .email(userEntity.getEmail())
            .userType(userEntity.userType)
            .build();
}
