package com.tanerdundar.sharer.requests.follow;

import com.tanerdundar.sharer.entities.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class FollowCreateRequest {

    private long followerId;
    private long followingId;

}
