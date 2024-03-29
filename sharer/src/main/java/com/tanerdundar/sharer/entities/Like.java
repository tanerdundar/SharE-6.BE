package com.tanerdundar.sharer.entities;


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

    @OneToOne
    @JoinColumn(name="user_id", nullable = false)
    private User liker;

    @OneToOne
    @JoinColumn(name="meow_id", nullable = false)
    private Meow likedMeow;

    @Enumerated(EnumType.STRING)
    @Column (name="like_status")
    private Status likeStatus;

    @PrePersist
    public void prePersist() {
        this.likeStatus=((this.likeStatus==Status.ACTIVE)?Status.INACTIVE:Status.ACTIVE);
    }

}
