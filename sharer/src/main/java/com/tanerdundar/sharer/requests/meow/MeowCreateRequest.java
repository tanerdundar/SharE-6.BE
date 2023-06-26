package com.tanerdundar.sharer.requests.meow;

import lombok.Data;

@Data
public class MeowCreateRequest {

    long ownerId;
    String content;
}
