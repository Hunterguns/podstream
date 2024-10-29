package com.sandeep.podstream.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    public UUID id;
    public String username;
    public String email;
    public String userType;
    public String password;
    public String confirmPassword;
    public String newUserName;
    public String newEmail;
}
