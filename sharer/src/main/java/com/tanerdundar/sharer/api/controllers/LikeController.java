package com.tanerdundar.sharer.api.controllers;

import com.tanerdundar.sharer.requests.like.LikeCreateRequest;
import com.tanerdundar.sharer.service.abstracts.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{userId}/{meowId}")
    public void createNewLike(@PathVariable long userId, @PathVariable long meowId) {
        likeService.createNewLike(userId,meowId);
    }
    @GetMapping("/{userId}/{meowId}")
    public boolean checkLikeOrNot(@PathVariable long userId, @PathVariable long meowId){
        return likeService. checkLikeOrNot(userId,meowId);
    }
    @DeleteMapping("/{userId}/{meowId}")
    public void deleteLike(@PathVariable long userId, @PathVariable long meowId){
        likeService.deleteLike(userId,meowId);
    }

}
