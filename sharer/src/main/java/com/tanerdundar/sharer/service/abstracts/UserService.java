package com.tanerdundar.sharer.service.abstracts;

import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.requests.user.UserLoginRequest;
import com.tanerdundar.sharer.requests.user.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getOneUserByUserId(long userId);
    List<User> getAllUsers();
    PseudoUser createOneUser(UserCreateRequest request);
    PseudoUser createOneAdminUser(UserCreateRequest request,long userId);

    long userLogin(UserLoginRequest request);
    PseudoUser getOnePseudoUserByUserId(long userId, PseudoUser pNewUser);

    PseudoUser getOnePseudoUserByUsername(String username, long followerId);
    PseudoUser getOnePseudoUserByUsername(String username);

    List<PseudoUser> getAllFollowersPseudoByUserId(long ownerId,long userId);

    List<PseudoUser> getAllFollowingsPseudoByUserId(long ownerId,long userId);

    void updateOneUserName(long userId, UserUpdateRequest request);

    List<PseudoUser> getAllUsersForZeroFollow();

    List<String> getUsersName(String username);
}
