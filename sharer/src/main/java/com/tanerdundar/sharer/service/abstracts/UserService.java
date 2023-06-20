package com.tanerdundar.sharer.service.abstracts;

import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.UserCreateRequest;
import com.tanerdundar.sharer.requests.UserLoginRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    User getOneUserByUserId(long userId);

    List<User> getAllUsers();


    User createOneUser(UserCreateRequest request);

    boolean userLogin(UserLoginRequest request);
}
