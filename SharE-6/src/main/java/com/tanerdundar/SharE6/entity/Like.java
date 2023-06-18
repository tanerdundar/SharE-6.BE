package com.tanerdundar.SharE6.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="likes")
public class Like {

    @Id
    @Column(name="like_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long likeId;
}
