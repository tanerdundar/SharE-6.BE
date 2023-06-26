package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.requests.follow.FollowCreateRequest;
import com.tanerdundar.sharer.service.abstracts.FollowService;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class FollowManager implements FollowService {


    private final FollowRepository followRepository;
    private final UserService userService;


    @Override
    public void createNewFollow(FollowCreateRequest request,long userId) {
        Follow newFollow= new Follow();
        newFollow.setFollower(userService.getOneUserByUserId(userId));
        newFollow.setFollowing(userService.getOneUserByUserId(request.getFollowingId()));
        followRepository.save(newFollow);
    }

    @Override
    public List<Follow> getFollowsByUerId(long userId) {
        return followRepository.findFollowsByFollower_UserId(userId);
    }
}
