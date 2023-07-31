package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.LikeRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.dto.PseudoMeow;
import com.tanerdundar.sharer.dto.PseudoUser;
import com.tanerdundar.sharer.entities.*;
import com.tanerdundar.sharer.exceptionHandlers.exceptions.MeowException;
import com.tanerdundar.sharer.requests.meow.MeowCreateRequest;
import com.tanerdundar.sharer.service.abstracts.MeowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.*;


@RequiredArgsConstructor
@Service
public class MeowManager implements MeowService {

    private final MeowRepository meowRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final LikeRepository likeRepository;


    @Override
    public boolean createNewMeow(MeowCreateRequest request) {
        if(request.getContent().length()>189){
            throw new MeowException("Type max 189 character please");
        } if(request.getContent().length()<1){
            throw new MeowException("Type at least 1 character please");
        }

            Meow newMeow = new Meow();
            User owner = userRepository.findById(request.getOwnerId()).orElseThrow();
            newMeow.setContent(request.getContent());
            newMeow.setOwner(owner);
            newMeow.setMeowStatus(owner.getUserStatus());
            newMeow.setMeowDate(ZonedDateTime.now());
            meowRepository.save(newMeow);
            return true;
    }

    @Override
    public List<PseudoMeow> getOneUsersMeowsByUserId(long userId) {
        List<Meow> myMeows= meowRepository.findMeowsByOwner_UserId(userId);
        PseudoUser pOwner=new PseudoUser(userRepository.findById(userId));
        List<PseudoMeow> returning = new ArrayList<>();
        for (int i=0;i< myMeows.size();i++){
            PseudoMeow pMeow= new PseudoMeow(myMeows.get(i), likeRepository.existsLikeByLikedMeow_MeowIdAndLiker_UserId(myMeows.get(i).getMeowId(),userId),pOwner );
            List<Like> likes=likeRepository.findAllByLikedMeow_MeowId(myMeows.get(i).getMeowId());
            List<PseudoUser> likedUsers= new ArrayList<>();
            for(int j=0;j< likes.size();j++){
                Optional<User> liker= userRepository.findById(likes.get(j).getLiker().getUserId());
                PseudoUser newPseudoUser= new PseudoUser(liker);
                likedUsers.add(newPseudoUser);
            }
            pMeow.setLikedUsers(likedUsers);
            returning.add(pMeow);
        }
        Comparator<PseudoMeow> idComparator = Comparator.comparingLong(PseudoMeow::getMeowId);
        Comparator<PseudoMeow> reverseIdComparator = Collections.reverseOrder(idComparator);
        Collections.sort(returning, reverseIdComparator);
        return  returning;
    }

    @Override
    public List<PseudoMeow> getHomeMeowsByUserId(long userId) {
        List<Follow> followings = followRepository.findFollowsByFollower_UserId(userId);
        List<PseudoMeow> meows= new ArrayList<>();
        for (Follow following : followings) {
            Optional<User> user = userRepository.findById(following.getFollowing().getUserId());
            if(!(following.getFollowStatus()== Status.INACTIVE)){
                PseudoUser pUser = new PseudoUser(user,false);
                List<Meow> userMeows = meowRepository.findMeowsByOwner_UserId(pUser.getUserId());
                for (Meow userMeow : userMeows) {
                    boolean like= likeRepository.existsLikeByLikedMeow_MeowIdAndLiker_UserId(userMeow.getMeowId(),userId);
                    PseudoMeow addingMeow= new PseudoMeow(userMeow,like,pUser);
                    List<Like> likes=likeRepository.findAllByLikedMeow_MeowId(userMeow.getMeowId());
                    List<PseudoUser> likedUsers= new ArrayList<>();
                    for(int i=0;i< likes.size();i++){
                        Optional<User> liker= userRepository.findById(likes.get(i).getLiker().getUserId());
                        PseudoUser newPseudoUser= new PseudoUser(liker);
                        likedUsers.add(newPseudoUser);
                    }
                    addingMeow.setLikedUsers(likedUsers);
                    meows.add(addingMeow);
                }
            }

        }
        Comparator<PseudoMeow> idComparator = Comparator.comparingLong(PseudoMeow::getMeowId);
        Comparator<PseudoMeow> reverseIdComparator = Collections.reverseOrder(idComparator);
        Collections.sort(meows, reverseIdComparator);
        return meows;
    }

    @Override
    public List<PseudoMeow> getAllMeowsPseudoByUserId(long userId,long ownerId) {
        List<Meow> allMeows =meowRepository.findMeowsByOwner_UserId(userId);
        List<PseudoMeow> newList= new ArrayList<>();
        for(int i=0;i<allMeows.size();i++){
            boolean result =likeRepository.existsLikeByLikedMeow_MeowIdAndLiker_UserId(allMeows.get(i).getMeowId(),ownerId);
            PseudoUser owner = new PseudoUser(userRepository.findById(userId));
            PseudoMeow newPseudo = new PseudoMeow(allMeows.get(i),result,owner);
            List<Like> likes=likeRepository.findAllByLikedMeow_MeowId(newPseudo.getMeowId());
            List<PseudoUser> likedUsers= new ArrayList<>();
            for(int j=0;j< likes.size();j++){
                Optional<User> liker= userRepository.findById(likes.get(j).getLiker().getUserId());
                PseudoUser newPseudoUser= new PseudoUser(liker);
                likedUsers.add(newPseudoUser);
            }
            newPseudo.setLikedUsers(likedUsers);
            newList.add(newPseudo);

        }
        Comparator<PseudoMeow> idComparator = Comparator.comparingLong(PseudoMeow::getMeowId);
        Comparator<PseudoMeow> reverseIdComparator = Collections.reverseOrder(idComparator);
        Collections.sort(newList, reverseIdComparator);
        return newList;
    }
}
