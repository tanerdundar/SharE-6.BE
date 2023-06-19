package com.tanerdundar.sharer.api.controllers;

import com.tanerdundar.sharer.service.abstracts.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/follows")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;


}
