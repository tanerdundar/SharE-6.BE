package com.tanerdundar.sharer.dto;

import com.tanerdundar.sharer.entities.Rank;
import com.tanerdundar.sharer.entities.Status;
import com.tanerdundar.sharer.entities.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;


@Data
@NoArgsConstructor

public class PseudoUser {

    private long userId;
    private String username;
    private String name;
    private String email;
    private Status userStatus;
    private Rank userRank;
    private String backgroundColor;
    private boolean follow;
    private long numberOfMeows;
    private long numberOfFollowings;
    private long numberOfFollowers;


    public PseudoUser(User user){
        this.userId=user.getUserId();
        this.username=user.getUsername();
        this.name=user.getName();
        this.email=user.getEmail();
        this.userStatus=user.getUserStatus();
        this.userRank=user.getUserRank();
        this.backgroundColor=user.getBackgroundColor();
    }
    public PseudoUser(Optional<User> user){
        this.userId=user.get().getUserId();
        this.username=user.get().getUsername();
        this.name=user.get().getName();
        this.email=user.get().getEmail();
        this.userStatus=user.get().getUserStatus();
        this.userRank=user.get().getUserRank();
        this.backgroundColor=user.get().getBackgroundColor();
    }
    public PseudoUser(Optional<User> user,boolean follow){

            this.userId=user.get().getUserId();
            this.username=user.get().getUsername();
            this.name=user.get().getName();
            this.email=user.get().getEmail();
            this.userStatus=user.get().getUserStatus();
            this.userRank=user.get().getUserRank();
            this.backgroundColor=user.get().getBackgroundColor();
            this.follow=follow;
    }

    public PseudoUser newPseudo(Optional<User> user,boolean follow){
        PseudoUser newPseudo = new PseudoUser();

        newPseudo.setUserId(user.get().getUserId());
        newPseudo.setUsername(user.get().getUsername());
        newPseudo.setName(user.get().getName());
        newPseudo.setEmail(user.get().getEmail());
        newPseudo.setUserStatus(user.get().getUserStatus());
        newPseudo.setUserRank(user.get().getUserRank());
        newPseudo.setBackgroundColor(user.get().getBackgroundColor());
        newPseudo.setFollow(follow);
        return newPseudo;
    }
}
