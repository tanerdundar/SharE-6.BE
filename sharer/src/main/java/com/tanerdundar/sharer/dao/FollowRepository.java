package com.tanerdundar.sharer.dao;

import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.requests.follow.FollowCheckRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {

//    List<Long> findFollowIdByFollowerId(long userId);

    Optional<Follow> findFollowsByFollower_UserIdAndFollowing_UserId(long userId, long followerId);

    List<Follow> findFollowsByFollower_UserId(long userId);

   // boolean existsFollowByFollower_UserIdAndFollowing_UserId(long userId, long followerId);

    boolean existsFollowByFollower_UserIdAndFollowing_UserId(long followerId, long followingId);


}
