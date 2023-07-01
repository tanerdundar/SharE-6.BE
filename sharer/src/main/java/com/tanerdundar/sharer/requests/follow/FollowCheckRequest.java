package com.tanerdundar.sharer.requests.follow;

import lombok.Data;

@Data
public class FollowCheckRequest {

    private long userId;
    private long searchedUserId;
}
