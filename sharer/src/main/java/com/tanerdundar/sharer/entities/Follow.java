package com.tanerdundar.sharer.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="follows")
@Data
public class Follow {

    @Id
    @Column(name="follow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long followId;

    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private User follower;

    @ManyToOne
    @JoinColumn(name = "following_id", nullable = false)
    private User following;

    @Enumerated(EnumType.STRING)
    @Column (name="follow_status")
    private Status followStatus;

    @PrePersist
    public void prePersist() {

        if(this.followStatus==null) {
            this.followStatus = Status.ACTIVE;
        }else{
            this.followStatus=((this.followStatus==Status.ACTIVE)?Status.INACTIVE:Status.ACTIVE);
        }
    }

}
