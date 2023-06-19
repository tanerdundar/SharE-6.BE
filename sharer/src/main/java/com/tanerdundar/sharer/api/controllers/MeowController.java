package com.tanerdundar.sharer.api.controllers;

import com.tanerdundar.sharer.service.abstracts.MeowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/meows")
@RequiredArgsConstructor
public class MeowController {

    private final MeowService meowService;


}
