package com.tanerdundar.SharE6.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
@Table(name="meows")
public class Meow {

    @Id
    @Column(name="meow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long meow_id;

    @Column(name="content")
    private String content;

//    @JoinColumn(name = "user_id", nullable = false)
//    @ManyToOne
//    private User owner;

    @Column(name="status")
    private Status meowStatus;

//    @Column(name="number_of_likes")
//    private long numberOfLikes;

    @Column(name="meow_date")
    private ZonedDateTime meowDate;

}
