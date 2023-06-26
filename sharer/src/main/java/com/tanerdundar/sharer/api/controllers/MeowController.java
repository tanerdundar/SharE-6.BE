package com.tanerdundar.sharer.api.controllers;

import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.requests.meow.MeowCreateRequest;
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

    @PostMapping
    public boolean createNewMeow(@RequestBody MeowCreateRequest request) {
        return meowService.createNewMeow(request);
    }
    @GetMapping("/{userId}")
    public ResponseEntity getOneUsersMeowsByUserId(@PathVariable long userId){
        List<Meow> meows =meowService.getOneUsersMeowsByUserId(userId);
        return ResponseEntity.ok(meows);
    }
    @GetMapping("/home/{userId}")
    public ResponseEntity getHomeMeowsByUserId(@PathVariable long userId) {
        List<Meow> homeMeows = meowService.getHomeMeowsByUserId(userId);
        return ResponseEntity.ok(homeMeows);
    }


}
