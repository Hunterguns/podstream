package com.sandeep.podstream.service;

import com.sandeep.podstream.core.entity.UserEntity;
import com.sandeep.podstream.model.LoginResponse;
import com.sandeep.podstream.model.User;
import com.sandeep.podstream.model.requests.UserRequest;
import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    User registerUser(UserRequest userRequest);

    @Transactional
    boolean deleteUserById(UUID userId);

    User getUser(UserRequest userRequest) throws Exception;

    Optional<UserEntity> getUserByIdOrUsernameOrEmail(UUID id, String username, String email) throws Exception;

    boolean updateUser(UserRequest userRequest);

    LoginResponse userLogin(UserRequest userRequest) throws Exception;

}
