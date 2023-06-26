package util;

import com.tanerdundar.sharer.entities.Rank;
import com.tanerdundar.sharer.entities.Status;
import com.tanerdundar.sharer.entities.User;
import lombok.Data;

import java.util.Optional;

@Data
public class PseudoUser {

    private long userId;
    private String username;
    private String name;
    private String email;
    private Status userStatus;
    private Rank userRank;
    private String backgroundColor;

    public PseudoUser newPseudo(Optional<User> user){
        PseudoUser newPseudo = new PseudoUser();

        newPseudo.setUserId(user.get().getUserId());
        newPseudo.setUsername(user.get().getUsername());
        newPseudo.setName(user.get().getName());
        newPseudo.setEmail(user.get().getEmail());
        newPseudo.setUserStatus(user.get().getUserStatus());
        newPseudo.setUserRank(user.get().getUserRank());
        newPseudo.setBackgroundColor(user.get().getBackgroundColor());

        return newPseudo;
    }
}
