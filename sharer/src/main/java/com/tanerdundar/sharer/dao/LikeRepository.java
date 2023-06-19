package com.tanerdundar.sharer.dao;

import com.tanerdundar.sharer.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository  extends JpaRepository<Like,Long> {

}