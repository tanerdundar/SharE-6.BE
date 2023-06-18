package com.tanerdundar.SharE6.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="follows")
@Data
public class Follow {

    @Id
    @Column(name="follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long follow_id;
}
