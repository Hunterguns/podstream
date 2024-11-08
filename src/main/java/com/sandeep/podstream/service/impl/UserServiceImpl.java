package com.sandeep.podstream.service.impl;

import com.sandeep.podstream.core.entity.UserEntity;
import com.sandeep.podstream.model.LoginResponse;
import com.sandeep.podstream.model.User;
import com.sandeep.podstream.model.requests.UserRequest;
import com.sandeep.podstream.repository.UserRepository;
import com.sandeep.podstream.service.Auth0JwtService;
import com.sandeep.podstream.service.UserService;
import graphql.com.google.common.base.Strings;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.sandeep.podstream.constants.UserTypes.FREE_USER;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final Auth0JwtService auth0JwtService;

    @Override
    public User registerUser(UserRequest userRequest) {
        if (Strings.isNullOrEmpty(userRequest.getPassword()) || Strings.isNullOrEmpty(userRequest.confirmPassword) || !userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            log.error("Password and confirm password are mandatory and should match");
        }
        Optional<UserEntity> existingUser = userRepository.findByEmail(userRequest.getEmail());
        if (existingUser.isPresent()) {
            //throw exception
            log.error("User with email already exists");
            return null;
        }

        UserEntity userEntity = UserEntity.builder()
                .username(userRequest.getUsername())
                .email(userRequest.getEmail())
                .userType(FREE_USER.name())
                .hashedPassword(userRequest.password)
                .build();

        UserEntity savedUser = userRepository.save(userEntity);

        return UserEntity.toUser.apply(savedUser);
    }

    @Override
    @Transactional
    public boolean deleteUserById(UUID userId) {
        try {
            userRepository.deleteById(userId);
            return true;
        } catch (Exception e) {
            log.error("Unable to delete user, reason: ", e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(UserRequest userRequest) throws Exception {
        Optional<UserEntity> userEntity = this.getUserByIdOrUsernameOrEmail(userRequest.getId(), userRequest.getNewUserName(), userRequest.getEmail());
        if (userEntity.isEmpty()) {
            throw new Exception("User with provided details doesn't exists");
        }
        return UserEntity.toUser.apply(userEntity.get());
    }

    @Override
    public Optional<UserEntity> getUserByIdOrUsernameOrEmail(UUID id, String username, String email) throws Exception {
        Optional<UserEntity> userEntity = null;
        if (Objects.nonNull(id)) {
            userEntity = userRepository.findById(id);
        } else if (!Strings.isNullOrEmpty(username)) {
            userEntity = userRepository.findByUsername(username);
        } else if (!Strings.isNullOrEmpty(email)) {
            userEntity = userRepository.findByEmail(email);
        } else {
            log.error("Please provide at least one of the following: userId, username, email");
        }
        return userEntity;
    }

    @Override
    @Transactional
    public boolean updateUser(UserRequest userRequest) {
        try {
            Optional<UserEntity> optionalUserEntity = userRepository.findById(userRequest.getId());
            if (optionalUserEntity.isPresent()) {
                UserEntity userEntity = optionalUserEntity.get();
                Optional.ofNullable(userRequest.getNewUserName()).ifPresent(u -> userEntity.setUsername(userRequest.getNewUserName()));
                Optional.ofNullable(userRequest.getNewEmail()).ifPresent(u -> userEntity.setEmail(userRequest.getNewEmail()));
                return true;
            }
            return false;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public LoginResponse userLogin(UserRequest userRequest) throws Exception {
        Optional<UserEntity> optionalUserEntity = this.getUserByIdOrUsernameOrEmail(userRequest.getId(), userRequest.getUsername(), userRequest.getEmail());
        if (optionalUserEntity.isEmpty()) {
            throw new Exception("User with provided details doesn't exists");
        }
        UserEntity userEntity = optionalUserEntity.get();
        String jwtToken = null;
        if (userRequest.getPassword().equals(userEntity.getHashedPassword())) {
            jwtToken = auth0JwtService.generateJwtToken(userEntity);
        }
        return LoginResponse.builder()
                .username(userEntity.getUsername())
                .accessToken(jwtToken)
                .build();
    }


}
