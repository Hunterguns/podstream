package com.sandeep.podstream.controller;

import com.sandeep.podstream.model.LoginResponse;
import com.sandeep.podstream.model.User;
import com.sandeep.podstream.model.requests.UserRequest;
import com.sandeep.podstream.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @QueryMapping(value = "getUser")
    public User getUser(@Argument UserRequest userRequest) throws Exception {
        return userService.getUser(userRequest);
    }

    @MutationMapping(value = "createUser")
    public User createUser(@Argument("userRequest") UserRequest userRequest) {
        return userService.registerUser(userRequest);
    }

    @MutationMapping(value = "updateUser")
    public String updateUser(@Argument UserRequest userRequest) {
        return userService.updateUser(userRequest) ? "Successfully updated User" : "Something went wrong while updating the user. Please try again after sometime";
    }

    @MutationMapping(value = "deleteUserById")
    public String deleteUserById(@Argument UUID id) {
        return userService.deleteUserById(id) ? "Successfully deleted user" : "Unable to delete user. Please try again.";
    }

    @MutationMapping(value = "userLogin")
    public LoginResponse userLogin(@Argument UserRequest userRequest) throws Exception {
        return userService.userLogin(userRequest);
    }

}
