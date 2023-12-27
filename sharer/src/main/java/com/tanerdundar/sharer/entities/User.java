package com.tanerdundar.sharer.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.UserException;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Entity
@Table (name="users")
@Data
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;

    @Column(name="username")
    private String username;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column (name="user_status")
    private Status userStatus;

    @Enumerated(EnumType.STRING)
    @Column (name="user_rank")
    private Rank userRank;

    @JsonIgnore
    @OneToMany(mappedBy = "owner")
    private List<Meow> meows;

    @JsonIgnore
    @OneToMany (mappedBy = "username")
    private  List<User> followers;

    @JsonIgnore
    @OneToMany (mappedBy = "username")
    private  List<User> followings;

    @Column(name="background_color")
    private String backgroundColor;

    @PrePersist
    public void prePersist() {
        if(this.userRank==Rank.ADMIN){
            this.userRank=Rank.ADMIN;
        }else {
            this.userRank=Rank.STANDARD;
        }
        if(this.userStatus==Status.INACTIVE) {
        }else {
            this.userStatus = Status.ACTIVE;
        }
        if(this.backgroundColor==null){
            this.backgroundColor=colorPicker();
        }

    }
    private String colorPicker() {
        String color="#";
        String[] colors ={"#00FFFF", "#808080", "#000080", "#C0C0C0", "#008080", "#808000", "#008000", "#0000FF", "#00FF00", "#800080", "#FF00FF", "#800000", "#FF0000", "#FFFF00"};

        int p =  (int)Math.round((Math.random()*14)-0.5);
            color=colors[p];
        return color;
    }

    static public void validate(User user){
        if(user.getName().length()<3){
            throw new UserException("Username can not be shorter than 3 character!..");
        }
    }
}