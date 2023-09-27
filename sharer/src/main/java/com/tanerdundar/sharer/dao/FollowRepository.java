package com.tanerdundar.sharer.dao;

import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.entities.Status;
import com.tanerdundar.sharer.requests.follow.FollowCheckRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow,Long> {


    Optional<Follow> findFollowsByFollower_UserIdAndFollowing_UserId(long userId, long followerId);

    List<Follow> findFollowsByFollower_UserId(long userId);

    List<Follow> findFollowsByFollowing_UserId(long userId);

//    List<Follow> findFollowsByFollower_UserIdAndFollowStatusActive(long userId, Status status);
  //List<Follow> findFollowsByFollowing_UserIdAndFollowStatusActive(long userId,Status status);
//    List<Follow> findFollowsByFollowing_UserIdAndAndFollowStatus(long userId,Status status);
 //   List<Follow> findFollowsByFollowing_UserIdAndFollowStatusActive(long userId,Status statu);
//    List <Follow> findFollowsByFollower_UserIdAndFollowStatus_ACTIVE(long userId);
//    List<Follow> findFollowsByFollowing_UserIdAndFollowStatus_ACTIVE(long userId);
//    List<Follow> findFollowsByFollowing_UserIdAndFollowStatus_Inactive(long userId,Status statu);
    List<Follow> findFollowsByFollowing_UserIdAndFollowStatus(long userId,Status statu);
    List<Follow> findFollowsByFollower_UserIdAndFollowStatus(long userId,Status statu);




    boolean existsFollowByFollower_UserIdAndFollowing_UserId(long followerId, long followingId);


}
