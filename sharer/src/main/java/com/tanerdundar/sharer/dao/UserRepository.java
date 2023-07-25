package com.tanerdundar.sharer.dao;

import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository <User,Long> {


    Optional<User> findByUsername(String username);






}
