package com.tanerdundar.sharer.requests.user;

import com.tanerdundar.sharer.entities.User;
import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;
    private String email;
    private String password;

    public User createOneUser() {
        User newUser = new User();
        newUser.setUsername(this.username);
        newUser.setEmail(this.email);
        newUser.setPassword(this.password);
        return newUser;
    }
}
