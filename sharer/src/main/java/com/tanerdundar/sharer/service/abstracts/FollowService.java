package com.tanerdundar.sharer.service.abstracts;


import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.requests.follow.FollowCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService  {


    void createNewFollow(FollowCreateRequest request,long userId);

    List<Follow> getFollowsByUerId(long userId);
}
