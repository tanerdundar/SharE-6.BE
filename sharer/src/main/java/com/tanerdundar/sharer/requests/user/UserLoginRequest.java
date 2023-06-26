package com.tanerdundar.sharer.requests.user;

import com.tanerdundar.sharer.entities.User;
import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;

}
