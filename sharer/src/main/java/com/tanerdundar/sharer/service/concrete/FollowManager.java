package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.entities.Status;
import com.tanerdundar.sharer.requests.follow.FollowCheckRequest;
import com.tanerdundar.sharer.requests.follow.FollowCreateRequest;
import com.tanerdundar.sharer.service.abstracts.FollowService;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class FollowManager implements FollowService {


    private final FollowRepository followRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    @Override
    public void createNewFollow(FollowCreateRequest request,long userId) {
        if(!followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).isPresent()){
            System.out.println(userId+" "+ request.getFollowingId()+"'i takip etmiyor" );
            Follow newFollow= new Follow();
            newFollow.setFollower(userService.getOneUserByUserId(userId));
            newFollow.setFollowing(userService.getOneUserByUserId(request.getFollowingId()));
            followRepository.save(newFollow);
        }else if(followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).
                get().getFollowStatus()==Status.INACTIVE){
            System.out.println("ortaya girdi ");
            Follow toUpdate = followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).get();
            toUpdate.setFollowStatus(Status.ACTIVE);
            followRepository.save(toUpdate);
        }else {
            System.out.println("asagi girdi ");

            Follow toUpdate = followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).get();
            toUpdate.setFollowStatus(Status.INACTIVE);
            followRepository.save(toUpdate);
        }

    }

    @Override
    public List<Follow> getFollowsByUserId(long userId) {
//        List<Follow> toPseudo =followRepository.findFollowsByFollower_UserId(userId);
//        PseudoUser newPFollower= new PseudoUser(userRepository.findById(userId));
//        for(int j=0;j<toPseudo.size();j++){
//            toPseudo.get(j).setFollower(newPFollower);
//        }
//        for(int i=0;i<toPseudo.size();i++) {
//            PseudoUser newP = new PseudoUser(toPseudo.get(i).getFollowing());
//            to
//        }


            return followRepository.findFollowsByFollower_UserId(userId);
    }

    @Override
    public boolean checkFollow(long userId,long searchedUserId) {
        Optional<Follow> follow = followRepository.findFollowsByFollower_UserIdAndFollowing_UserId(userId, searchedUserId);
        if (follow== null||follow.get().getFollowStatus()== Status.INACTIVE){
            return false;
        } else {
                return true;
            }
    }


}
