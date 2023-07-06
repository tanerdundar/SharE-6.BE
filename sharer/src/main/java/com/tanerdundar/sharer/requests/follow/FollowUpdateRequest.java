package com.tanerdundar.sharer.requests.follow;

import com.tanerdundar.sharer.entities.Status;
import lombok.Data;

@Data
public class FollowUpdateRequest {

    private Status followStatus;
}
