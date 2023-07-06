package com.tanerdundar.sharer.dao;

import com.tanerdundar.sharer.entities.Like;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository  extends JpaRepository<Like,Long> {

    boolean existsLikeByLikedMeow_MeowIdAndLiker_UserId( long meowId,long userId);

    boolean existsLikeByLikedMeowAndLiker(Meow meow, User user);

    List<Like> findAllByLikedMeow_MeowId(long meowId);

}