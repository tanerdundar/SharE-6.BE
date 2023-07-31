package com.tanerdundar.sharer.requests.user;

import com.tanerdundar.sharer.entities.User;
import lombok.Data;

@Data
public class UserCreateRequest {

    private String username;
    private String email;
    private String password;

    public User createOneUser() {
        User newUser = new User();
        newUser.setUsername(this.username);
        newUser.setEmail(this.email);
        newUser.setPassword(this.password);
      //  newUser.setBackgroundColor();
        return newUser;
    }
//    private String colorPicker() {
//        String color="#";
//        String[] colors ={"#00FFFF", "#808080", "#000080", "#C0C0C0", "#008080", "#808000", "#008000", "#0000FF", "#00FF00", "#800080", "#FF00FF", "#800000", "#FF0000", "#FFFF00"};
//
//        int p =  (int)Math.round((Math.random()*14)-0.5);
//        color=colors[p];
//        return color;
//    }
}
