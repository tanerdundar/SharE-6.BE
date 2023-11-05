package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.*;
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
    public PseudoUser createOneUser(UserCreateRequest request) {

        User user =checkUserNameAndEmailValidation(request);
        userRepository.save(user);
        return new PseudoUser(user);
    }




    @Override
    public PseudoUser createOneAdminUser(UserCreateRequest request,long userId) {
        if(userRepository.findById(userId).isPresent()&&userRepository.findById(userId).get().getUserRank()==Rank.ADMIN){
           User user =checkUserNameAndEmailValidation(request);
            user.setUserRank(Rank.ADMIN);

            userRepository.save(user);
            return new PseudoUser(user);
        } else throw new UserException("You dont have authorization!..");

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
        long followers= followRepository.findFollowsByFollowing_UserIdAndFollowStatus(userId,Status.ACTIVE).size();
        long followings= followRepository.findFollowsByFollower_UserIdAndFollowStatus(userId,Status.ACTIVE).size();
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
        pseudo.setNumberOfFollowers(followRepository.findFollowsByFollowing_UserIdAndFollowStatus(pseudo.getUserId(),Status.ACTIVE).size());
        pseudo.setNumberOfFollowings(followRepository.findFollowsByFollower_UserIdAndFollowStatus(pseudo.getUserId(),Status.ACTIVE).size());
        pseudo.setNumberOfMeows(meowRepository.findMeowsByOwner_UserId(pseudo.getUserId()).size());

        return pseudo;
    } else{
            throw new UserException();

        }
   }

    @Override
    public PseudoUser getOnePseudoUserByUsername(String username) {
        return new PseudoUser(userRepository.findByUsername(username));
    }

    @Override
    public List<PseudoUser> getAllFollowersPseudoByUserId(long ownerId,long userId) {
        List<Follow> allFollows =followRepository.findFollowsByFollowing_UserIdAndFollowStatus( userId,Status.ACTIVE);
        List<PseudoUser> newList= new ArrayList<>();
        List<PseudoUser> newReturnList= new ArrayList<>();
        for (Follow allFollow : allFollows) {
            PseudoUser newPseudo = new PseudoUser(userRepository.findById(allFollow.getFollower().getUserId()));
           newReturnList=setFollowersAndFollowings(newList, newPseudo);
        }
        return newReturnList;
    }




    @Override
    public List<PseudoUser> getAllFollowingsPseudoByUserId(long ownerId,long userId) {
        List<Follow> allFollows =followRepository.findFollowsByFollower_UserIdAndFollowStatus(userId,Status.ACTIVE);
        List<PseudoUser> newList= new ArrayList<>();
        List<PseudoUser> newReturnList= new ArrayList<>();
        for (Follow allFollow : allFollows) {
            PseudoUser newPseudo = new PseudoUser(userRepository.findById(allFollow.getFollowing().getUserId()));
           newReturnList= setFollowersAndFollowings(newList, newPseudo);
        }
        return newReturnList;
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

    @Override
    public List<PseudoUser> getAllUsersForZeroFollow() {
        List<PseudoUser> users = new ArrayList<>();
        for (int i=0;i<userRepository.findAll().size();i++){
            PseudoUser nUser = new PseudoUser(userRepository.findAll().get(i));
            nUser.setNumberOfMeows(meowRepository.findMeowsByOwner_UserId(nUser.getUserId()).size());
            nUser.setNumberOfFollowings(followRepository.findFollowsByFollower_UserIdAndFollowStatus(nUser.getUserId(),Status.ACTIVE).size());
            nUser.setNumberOfFollowers(followRepository.findFollowsByFollowing_UserIdAndFollowStatus(nUser.getUserId(),Status.ACTIVE).size());
            users.add(nUser);
        }
        return users;
    }

    @Override
    public List<String> getUsersName(String username) {
        List<User> users = userRepository.findAllByUsernameContains(username);
        List<String> userName = new ArrayList<String>();
        if (users.size() > 3) {
            for (int i=0;i<4;i++){
                userName.add(users.get(i).getUsername());
            }
        } else {
            for (User user : users) {
                userName.add(user.getUsername());
            }
        }

        return userName;
    }

    private User checkUserNameAndEmailValidation(UserCreateRequest request) {
        List<User> users=userRepository.findAll();
        for (User item : users) {
            if (request.getUsername().equals(item.getUsername())) {
                throw new UserException("Existing username!...");
            } else if (request.getUsername().length() < 5) {
                throw new UserException("At least 5 character please!.. ");
            }
        }
        for (User value : users) {
            String regex = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,6}$";
            if (request.getEmail().equals(value.getEmail())) {
                throw new EmailException("Existing email address!...");
            }
            if (!request.getEmail().matches(regex)) {
                throw new EmailException("A valid email please!...");
            }

        }

        return request.createOneUser();
    }

    private List<PseudoUser> setFollowersAndFollowings(List<PseudoUser> newList, PseudoUser newPseudo) {
        List<Follow> followings = followRepository.findFollowsByFollower_UserIdAndFollowStatus(newPseudo.getUserId(), Status.ACTIVE);
        List<Follow> followers = followRepository.findFollowsByFollowing_UserIdAndFollowStatus(newPseudo.getUserId(), Status.ACTIVE);
        List<Meow> meows = meowRepository.findMeowsByOwner_UserId(newPseudo.getUserId());
        newPseudo.setNumberOfFollowings(followings.size());
        newPseudo.setNumberOfFollowers(followers.size());
        newPseudo.setNumberOfMeows(meows.size());
        newList.add(newPseudo);
        return newList;
    }
}




