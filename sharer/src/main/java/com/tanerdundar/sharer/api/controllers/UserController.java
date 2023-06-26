package com.tanerdundar.sharer.api.controllers;


import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.requests.user.UserLoginRequest;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import util.PseudoUser;

import java.util.List;


@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

//    @GetMapping("/{userId}")
//    public ResponseEntity getOneUserByUserId(@PathVariable long userId) {
//        User user = userService.getOneUserByUserId(userId);
//        return ResponseEntity.ok(user);
//    }
    @GetMapping
    public ResponseEntity getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    @PostMapping
    public ResponseEntity createOneUser( @RequestBody UserCreateRequest request) {
        User user= userService.createOneUser(request);
        return ResponseEntity.ok(user);
    }
    @PostMapping("/login")
    public long userLogin(@RequestBody UserLoginRequest request) {
        long loggedUser=userService.userLogin(request);
        return loggedUser;
    }
    @GetMapping("/{userId}")
    public ResponseEntity getOnePseudoUserByUserId(@PathVariable long userId, PseudoUser pUser) {
        PseudoUser user = userService.getOnePseudoUserByUserId(userId,pUser);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/search/{username}")
    public ResponseEntity getOnePseudoUserByUsername(@PathVariable String username, PseudoUser pUser) {
        PseudoUser user = userService.getOnePseudoUserByUsername(username,pUser);
        return ResponseEntity.ok(user);
    }
    @GetMapping("/check/{username}")
    public ResponseEntity checkUserByUsername(@PathVariable String username) {
        boolean isThereThisUser = userService.checkUserByUsername(username);
        return ResponseEntity.ok(isThereThisUser);
    }

}
