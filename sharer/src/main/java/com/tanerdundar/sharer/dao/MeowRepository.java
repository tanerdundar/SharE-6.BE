package com.tanerdundar.sharer.dao;


import com.tanerdundar.sharer.entities.Meow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeowRepository extends JpaRepository<Meow,Long> {


}
