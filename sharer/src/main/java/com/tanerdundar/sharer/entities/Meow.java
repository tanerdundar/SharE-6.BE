package com.tanerdundar.sharer.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Data
@Table(name="meows")
public class Meow {

    @Id
    @Column(name="meow_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long meowId;

    @Column(name="content")
    private String content;

    @Column(name="status")
    private Status meowStatus;

    @Column(name="meow_date")
    private ZonedDateTime meowDate;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable= false)
    private User owner;

    @JsonIgnore
    @OneToMany(mappedBy = "username")
    private List<User> likedUsers;

}
