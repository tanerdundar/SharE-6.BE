package com.tanerdundar.sharer.api.controllers;


import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.UserCreateRequest;
import com.tanerdundar.sharer.requests.UserLoginRequest;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity getOneUserByUserId(@PathVariable long userId) {
        User user = userService.getOneUserByUserId(userId);
        return ResponseEntity.ok(user);
    }
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
    public boolean userLogin(@RequestBody UserLoginRequest request) {
        boolean logged=userService.userLogin(request);
        return logged;
    }
}
