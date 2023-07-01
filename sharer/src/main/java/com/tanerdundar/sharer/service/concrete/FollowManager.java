package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
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


    @Override
    public void createNewFollow(FollowCreateRequest request,long userId) {
       // boolean followCheck = followRepository.existsFollowByFollower_UserIdAndFollowing_UserId(userId, request.getFollowingId());
        if(!followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).isPresent()){
            System.out.println(userId+" "+ request.getFollowingId()+"'i takip etmiyor" );
            Follow newFollow= new Follow();
            newFollow.setFollower(userService.getOneUserByUserId(userId));
            newFollow.setFollowing(userService.getOneUserByUserId(request.getFollowingId()));
            followRepository.save(newFollow);
        }else if(followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).
                get().getFollowStatus()==Status.INACTIVE){
            System.out.println("ortaya girdi ");
            followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).
                    get().setFollowStatus(Status.ACTIVE);
        }else {
            System.out.println("asagi girdi ");

            followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).
                    get().setFollowStatus(Status.INACTIVE);
            System.out.println( followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).
                    get().getFollowId());
            System.out.println( followRepository.findFollowsByFollower_UserIdAndFollowing_UserId( userId, request.getFollowingId()).
                    get().getFollowing().getUserId());
        }

    }

    @Override
    public List<Follow> getFollowsByUserId(long userId) {
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
