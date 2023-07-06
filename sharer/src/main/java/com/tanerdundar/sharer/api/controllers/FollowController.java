package com.tanerdundar.sharer.api.controllers;

import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.requests.follow.FollowCreateRequest;
import com.tanerdundar.sharer.requests.follow.FollowCheckRequest;
import com.tanerdundar.sharer.requests.follow.FollowUpdateRequest;
import com.tanerdundar.sharer.requests.meow.MeowCreateRequest;
import com.tanerdundar.sharer.service.abstracts.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/{userId}")
    public void createNewFollow(@PathVariable long userId,@RequestBody FollowCreateRequest request) {
         followService.createNewFollow(request,userId);
    }
    @GetMapping("/{userId}")
    public ResponseEntity getFollowsByUserId( @PathVariable long userId){
        List<Follow> follows = followService.getFollowsByUserId(userId);
        return ResponseEntity.ok(follows);
    }
    @GetMapping("/checkFollow/{userId}/{searchedUserId}")
    public boolean checkFollow(@PathVariable long userId,@PathVariable long searchedUserId){
        System.out.println("asd");
        boolean isFollowing = followService.checkFollow(userId,searchedUserId);
                return isFollowing;
    }

//    @PutMapping("/follow/unfollow")
//    public Follow updateOneFollowStatus(@PathVariable Long followId, @RequestBody FollowUpdateRequest updateFollow) {
//        return followService.updateOneFollowStatusByRequest(followId, updateFollow);
//    }


}
