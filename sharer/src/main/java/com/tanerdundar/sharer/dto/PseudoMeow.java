package com.tanerdundar.sharer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.entities.Status;
import com.tanerdundar.sharer.entities.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class PseudoMeow {

    private long meowId;
    private String content;
    private Status meowStatus;
    private ZonedDateTime meowDate;
    private PseudoUser owner;
    private List<PseudoUser> likedUsers;
    private boolean isLiked;
    public PseudoMeow (Meow meow,boolean like,PseudoUser pUser) {
    this.meowId=meow.getMeowId();
    this.content=meow.getContent();
    this.meowStatus=meow.getMeowStatus();
    this.meowDate=meow.getMeowDate();
    this.owner=pUser;
    this.isLiked=like;
    }
}
