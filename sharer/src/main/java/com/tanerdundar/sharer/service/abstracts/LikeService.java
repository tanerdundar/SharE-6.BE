package com.tanerdundar.sharer.service.abstracts;

import com.tanerdundar.sharer.requests.like.LikeCreateRequest;
import org.springframework.stereotype.Service;

@Service
public interface LikeService {
    void createNewLike( long userId,long meowId);

    boolean checkLikeOrNot(long userId, long meowId);
    void deleteLike( long userId,long meowId);

}
