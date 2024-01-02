package com.tanerdundar.sharer.api.controllers;


import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.requests.user.UserLoginRequest;
import com.tanerdundar.sharer.requests.user.UserUpdateRequest;
import com.tanerdundar.sharer.service.abstracts.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping
    public ResponseEntity createOneUser( @RequestBody UserCreateRequest request) throws MessagingException {
        PseudoUser user= userService.createOneUser(request);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/{userId}")
    public void createOneAdminUser( @RequestBody UserCreateRequest request,@PathVariable long userId) {
        PseudoUser user= userService.createOneAdminUser(request,userId);
        // comment
    }
    @PostMapping("/login")
    public long userLogin(@RequestBody UserLoginRequest request) {
        return userService.userLogin(request);
    }
    @GetMapping("/{userId}")
    public ResponseEntity getOnePseudoUserByUserId(@PathVariable long userId, PseudoUser pUser) {
        PseudoUser user = userService.getOnePseudoUserByUserId(userId,pUser);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/check/{username}/{followerId}")
    public ResponseEntity checkUserByUsername(@PathVariable String username,@PathVariable long followerId){
        PseudoUser pseudo = userService.getOnePseudoUserByUsername(username,followerId);
        return ResponseEntity.ok(pseudo);
    }
    @GetMapping("/findSuggestions/{username}")
    public ResponseEntity findSuggestions(@PathVariable String username){
        List<String> users= userService.getUsersName(username);
        return ResponseEntity.ok(users);
    }
    @GetMapping("/{ownerId}/{userId}/followers")
    public ResponseEntity getAllFollowersByUserId(@PathVariable long ownerId,@PathVariable long userId) {
        List<PseudoUser> allFollowers=userService.getAllFollowersPseudoByUserId(ownerId,userId);
        return ResponseEntity.ok(allFollowers);
    }
    @GetMapping("/{ownerId}/{userId}/followings")
    public ResponseEntity getAllFollowingsByUserId(@PathVariable long ownerId,@PathVariable long userId) {
        List<PseudoUser> allFollowings=userService.getAllFollowingsPseudoByUserId(ownerId,userId);
        return ResponseEntity.ok(allFollowings);
    }
    @PutMapping("/{userId}")
    public void setUserName(@PathVariable long userId,@RequestBody UserUpdateRequest request){
        userService.updateOneUserName(userId,request);
    }
    @GetMapping("/zero")
    public ResponseEntity getAllUsersForZeroFollow() {
        List<PseudoUser> users = userService.getAllUsersForZeroFollow();
        return ResponseEntity.ok(users);
    }
    @GetMapping("/findToUpdate/{username}")
    public ResponseEntity getOneUserByUsername(@PathVariable String username){
        PseudoUser user = userService.getOnePseudoUserByUsername(username);
        return ResponseEntity.ok(user);
    }

}
