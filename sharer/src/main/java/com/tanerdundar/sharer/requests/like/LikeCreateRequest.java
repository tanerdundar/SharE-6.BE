package com.tanerdundar.sharer.requests.like;

import lombok.Data;

@Data
public class LikeCreateRequest {

    private long likerId;
    private long meowId;
}
