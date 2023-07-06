package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.LikeRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.entities.Like;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.entities.User;
import com.tanerdundar.sharer.requests.like.LikeCreateRequest;
import com.tanerdundar.sharer.service.abstracts.LikeService;
import com.tanerdundar.sharer.service.abstracts.MeowService;
import com.tanerdundar.sharer.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeManager implements LikeService {

    private final MeowRepository meowRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public void createNewLike(long userId, long meowId) {
//        boolean isThereThisLike= likeRepository.existsLikeByLikedMeow_MeowIdAndLiker_UserId(meowId,userId);
        User user= userRepository.findById(userId).get();
        Meow meow=meowRepository.findById(meowId).get();
        boolean isThereThisLike= likeRepository.existsLikeByLikedMeowAndLiker(meow,user);

        System.out.println();
        if(!isThereThisLike){
            Like newLike = new Like();
            newLike.setLikedMeow(meowRepository.findById(meowId).get());
            newLike.setLiker(userRepository.findById(userId).get());
            likeRepository.save(newLike);
        }

//        newLike.setLikedMeow(meowService.getOneMeowByMeowId(meowId));
//        newLike.setLiker(userService.getOneUserByUserId(userId));
    }

    @Override
    public boolean checkLikeOrNot(long userId, long meowId) {
        boolean result =likeRepository.existsLikeByLikedMeow_MeowIdAndLiker_UserId(meowId,userId);


        return result;
    }
}
