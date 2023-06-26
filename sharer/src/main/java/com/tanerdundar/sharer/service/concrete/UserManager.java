package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.PasswordException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.UserException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.EmailException;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.requests.user.UserLoginRequest;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import util.PseudoUser;

import java.util.List;
import java.util.Optional;

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

//    @Override
//    public boolean userLogin(UserLoginRequest request) {
//        List<User> users=userRepository.findAll();
//
//        for(int i=0;i<users.size();i++){
//            if(request.getUsername().equals(users.get(i).getUsername())){
//                if(request.getPassword().equals(users.get(i).getPassword())) {
//                    return true;
//                }
//            }
//        }
//        throw new PasswordException("Username or password is not true...");
//
//
//    }
@Override
public long userLogin(UserLoginRequest request) {
    Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
    if (optionalUser.isPresent()) {
        User user = optionalUser.get();
        if (request.getPassword().equals(user.getPassword())) {
            return user.getUserId();
        }
    }
    throw new PasswordException("Username or password is not true...");

}
    @Override
    public PseudoUser getOnePseudoUserByUserId(long userId,PseudoUser pNewUser) {
        Optional<User> user =userRepository.findById(userId);
        return pNewUser.newPseudo(user);
    }

    @Override
    public PseudoUser getOnePseudoUserByUsername(String username, PseudoUser pUser) {
        Optional<User> user =userRepository.findByUsername(username);
        return pUser.newPseudo(user);
    }

    @Override
    public boolean checkUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isPresent()){
            return true;
        } else
            throw new UserException();
    }

}




