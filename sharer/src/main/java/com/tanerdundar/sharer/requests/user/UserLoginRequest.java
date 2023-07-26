package com.tanerdundar.sharer.requests.user;

import lombok.Data;

@Data
public class UserLoginRequest {
    private String username;
    private String password;

}
