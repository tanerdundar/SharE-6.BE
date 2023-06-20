package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.PasswordException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.UserException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.EmailException;
import com.tanerdundar.sharer.requests.UserCreateRequest;
import com.tanerdundar.sharer.requests.UserLoginRequest;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserManager implements UserService {

    private final UserRepository userRepository;
    private final MeowRepository meowRepository;
    private final FollowRepository followRepository;


    @Override
    public User getOneUserByUserId(long userId) {
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createOneUser(UserCreateRequest request) {
        List<User> users=userRepository.findAll();
        for (int i=0;i<users.size();i++){
            if(request.getUsername().equals(users.get(i).getUsername())){
                throw new UserException("Existing username!...");
            }
        }
        for (int i=0;i<users.size();i++){
            if(request.getEmail().equals(users.get(i).getEmail())){
                throw new EmailException("Existing email address!...");
            }
        }

        return userRepository.save(request.createOneUser());
    }

    @Override
    public boolean userLogin(UserLoginRequest request) {
        List<User> users=userRepository.findAll();
        for(int i=0;i<users.size();i++){
            if(request.getUsername().equals(users.get(i).getUsername())){
                if(request.getPassword().equals(users.get(i).getPassword())) {
                    return true;
                }
            } else {
                throw new PasswordException("Username or password is not true...");
            }

        }
        return false;
    }

}




