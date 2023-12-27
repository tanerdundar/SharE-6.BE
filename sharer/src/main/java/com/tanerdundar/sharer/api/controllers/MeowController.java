package com.tanerdundar.sharer.api.controllers;

import com.tanerdundar.sharer.dto.PseudoMeow;
import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.meow.MeowCreateRequest;
import com.tanerdundar.sharer.requests.meow.MeowUpdateRequest;
import com.tanerdundar.sharer.requests.user.UserCreateRequest;
import com.tanerdundar.sharer.service.abstracts.MeowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/meows")
@RequiredArgsConstructor
public class MeowController {

    private final MeowService meowService;

    @GetMapping
    public ResponseEntity getAllUsers() {
        List<Meow> meows = meowService.getAllMeows();
        return ResponseEntity.ok(meows);
    }

    @PostMapping
    public boolean createNewMeow(@RequestBody MeowCreateRequest request) {
        return meowService.createNewMeow(request);
    }
    @GetMapping("/{userId}")
    public ResponseEntity getOneUsersMeowsByUserId(@PathVariable long userId){
        List<PseudoMeow> meows =meowService.getOneUsersMeowsByUserId(userId);
        return ResponseEntity.ok(meows);
    }
    @GetMapping("/home/{userId}")
    public ResponseEntity getHomeMeowsByUserId(@PathVariable long userId) {
        List<PseudoMeow> homeMeows = meowService.getHomeMeowsByUserId(userId);
        return ResponseEntity.ok(homeMeows);
    }
    @GetMapping("/{ownerId}/{userId}/meows")
    public ResponseEntity getAllMeowsByUserId(@PathVariable long userId,@PathVariable long ownerId) {
        List<PseudoMeow> allMeows=meowService.getAllMeowsPseudoByUserId(userId,ownerId);
        return ResponseEntity.ok(allMeows);
    }
    @PutMapping("/{ownerId}/{meowId}")
    public void updateOneMeowByMeowId(@PathVariable long ownerId,@PathVariable long meowId,@RequestBody MeowUpdateRequest request) {
        meowService.updateOneMeowByMeowId(ownerId,meowId,request);

    }



}
