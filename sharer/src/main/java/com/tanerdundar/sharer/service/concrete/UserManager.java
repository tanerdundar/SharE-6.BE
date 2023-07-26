package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.entities.Status;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.PasswordException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.UserException;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.EmailException;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.requests.user.UserLoginRequest;
import com.tanerdundar.sharer.requests.user.UserUpdateRequest;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        for (int i=0;i<users.size();i++) {
            if (request.getEmail().equals(users.get(i).getEmail())) {
                throw new EmailException("Existing email address!...");
            }
        }

        return userRepository.save(request.createOneUser());
    }
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
    public PseudoUser getOnePseudoUserByUserId(long userId, PseudoUser pNewUser) {
        Optional<User> user =userRepository.findById(userId);
        long followers= followRepository.findFollowsByFollowing_UserId(userId).size();
        long followings= followRepository.findFollowsByFollower_UserId(userId).size();
        long meows= meowRepository.findMeowsByOwner_UserId(userId).size();
        PseudoUser pseudo= pNewUser.newPseudo(user,true);
        pseudo.setNumberOfMeows(meows);
        pseudo.setNumberOfFollowers(followers);
        pseudo.setNumberOfFollowings(followings);
        return pseudo;
    }

    @Override
    public PseudoUser getOnePseudoUserByUsername(String username, long followerId) {
        Optional<User> user= userRepository.findByUsername(username);

        if(user.isPresent()){
        boolean follow = followRepository.existsFollowByFollower_UserIdAndFollowing_UserId(followerId,user.get().getUserId());
        if(followRepository.findFollowsByFollower_UserIdAndFollowing_UserId(followerId,user.get().getUserId()).isPresent()){
            if(followRepository.findFollowsByFollower_UserIdAndFollowing_UserId(followerId,user.get().getUserId()).get().getFollowStatus()== Status.INACTIVE){
                follow=!follow;
            }
        }

        PseudoUser pseudo = new PseudoUser(user,follow);
        pseudo.setNumberOfFollowers(followRepository.findFollowsByFollowing_UserId(pseudo.getUserId()).size());
        pseudo.setNumberOfFollowings(followRepository.findFollowsByFollower_UserId(pseudo.getUserId()).size());
        pseudo.setNumberOfMeows(meowRepository.findMeowsByOwner_UserId(pseudo.getUserId()).size());

        return pseudo;
    } else{
            throw new UserException();

        }
   }

    @Override
    public List<PseudoUser> getAllFollowersPseudoByUserId(long ownerId,long userId) {
        List<Follow> allFollows =followRepository.findFollowsByFollowing_UserId( userId);
        List<PseudoUser> newList= new ArrayList<>();
        for(int i=0;i<allFollows.size();i++){
            PseudoUser newPseudo = new PseudoUser(userRepository.findById(allFollows.get(i).getFollower().getUserId()));
            List<Follow> followings = followRepository.findFollowsByFollower_UserId(newPseudo.getUserId());
            List<Follow> followers = followRepository.findFollowsByFollowing_UserId(newPseudo.getUserId());
            List<Meow> meows =meowRepository.findMeowsByOwner_UserId(newPseudo.getUserId());
            newPseudo.setNumberOfFollowings(followings.size());
            newPseudo.setNumberOfFollowers(followers.size());
            newPseudo.setNumberOfMeows(meows.size());
            newList.add(newPseudo);
        }
        return newList;
    }

    @Override
    public List<PseudoUser> getAllFollowingsPseudoByUserId(long ownerId,long userId) {
        List<Follow> allFollows =followRepository.findFollowsByFollower_UserId(userId);
        List<PseudoUser> newList= new ArrayList<>();
        for(int i=0;i<allFollows.size();i++){
            PseudoUser newPseudo = new PseudoUser(userRepository.findById(allFollows.get(i).getFollowing().getUserId()));
            List<Follow> followings = followRepository.findFollowsByFollower_UserId(newPseudo.getUserId());
            List<Follow> followers = followRepository.findFollowsByFollowing_UserId(newPseudo.getUserId());
            List<Meow> meows =meowRepository.findMeowsByOwner_UserId(newPseudo.getUserId());
            newPseudo.setNumberOfFollowings(followings.size());
            newPseudo.setNumberOfFollowers(followers.size());
            newPseudo.setNumberOfMeows(meows.size());
            newList.add(newPseudo);
        }
        return newList;
    }

    @Override
    public void updateOneUserName(long userId, UserUpdateRequest request) {
       Optional<User> user = userRepository.findById(userId);
       if(user.isPresent()){
           User updateUser = user.get();
           updateUser.setName(request.getName());
           userRepository.save(updateUser);
       }

    }

}




