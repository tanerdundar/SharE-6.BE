package com.tanerdundar.sharer.service.concrete;

import com.tanerdundar.sharer.dao.FollowRepository;
import com.tanerdundar.sharer.dao.MeowRepository;
import com.tanerdundar.sharer.dao.UserRepository;
import com.tanerdundar.sharer.entities.Follow;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.entities.User;
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
    public List<Meow> getOneUsersMeowsByUserId(long userId) {
        Optional<User> meowOwner= userRepository.findById(userId);
        return  meowRepository.findMeowsByOwner_UserId(userId);
    }

    @Override
    public List<Meow> getHomeMeowsByUserId(long userId) {
        List<Follow> followings = followRepository.findFollowsByFollower_UserId(userId);
        List<Meow> meows= new ArrayList<>();
        for (Follow following : followings) {
            Optional<User> user = userRepository.findById(following.getFollowing().getUserId());
            List<Meow> userMeows = meowRepository.findMeowsByOwner_UserId(user.get().getUserId());
            for (Meow userMeow : userMeows) {
                meows.add(userMeow);
            }
        }
        Comparator<Meow> idComparator = Comparator.comparingLong(Meow::getMeow_id);
        Comparator<Meow> reverseIdComparator = Collections.reverseOrder(idComparator);
        Collections.sort(meows, reverseIdComparator);
        return meows;
    }
}
