package com.sandeep.podstream.service.impl;

import com.sandeep.podstream.core.entity.UserEntity;
import com.sandeep.podstream.model.User;
import com.sandeep.podstream.model.requests.UserRequest;
import com.sandeep.podstream.repository.UserRepository;
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
        }catch (Exception e){
            log.error("Unable to delete user, reason: ", e.getMessage());
            return false;
        }
    }

    @Override
    public User getUser(UserRequest userRequest) throws Exception {
        Optional<UserEntity> userEntity;
        if (Objects.nonNull(userRequest.getId())) {
            userEntity = userRepository.findById(userRequest.getId());
        } else if (!Strings.isNullOrEmpty(userRequest.getUsername())) {
            userEntity = userRepository.findByUsername(userRequest.getUsername());
        } else if (!Strings.isNullOrEmpty(userRequest.getEmail())) {
            userEntity = userRepository.findByEmail(userRequest.getEmail());
        } else {
            throw new Exception("Please provide at least one of the following: userId, username, email");
        }
        if(userEntity.isEmpty()){
            throw new Exception("User with provided details doesn't exists");
        }
        return UserEntity.toUser.apply(userEntity.get());
    }
}