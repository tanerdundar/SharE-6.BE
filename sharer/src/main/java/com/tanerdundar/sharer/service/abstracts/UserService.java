package com.tanerdundar.sharer.service.abstracts;

import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.requests.user.UserLoginRequest;
import org.springframework.stereotype.Service;
import util.PseudoUser;

import java.util.List;

@Service
public interface UserService {

    User getOneUserByUserId(long userId);
    List<User> getAllUsers();
    User createOneUser(UserCreateRequest request);
    long userLogin(UserLoginRequest request);
    PseudoUser getOnePseudoUserByUserId(long userId,PseudoUser pNewUser);
    PseudoUser getOnePseudoUserByUsername(String username, PseudoUser pUser);

    boolean checkUserByUsername(String username);
}
