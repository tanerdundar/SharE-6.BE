package com.tanerdundar.sharer.dao;


import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.entities.Meow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MeowRepository extends JpaRepository<Meow,Long> {

    List<Meow> findMeowsByOwner_UserId (long userId);
}
