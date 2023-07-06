package com.tanerdundar.sharer.service.abstracts;

import com.tanerdundar.sharer.dto.PseudoMeow;
import com.tanerdundar.sharer.entities.Meow;
import com.tanerdundar.sharer.requests.meow.MeowCreateRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MeowService {


    boolean createNewMeow(MeowCreateRequest request);

    List<Meow> getOneUsersMeowsByUserId(long userId);

    List<PseudoMeow> getHomeMeowsByUserId(long userId);

//    Meow getOneMeowByUserId(long userId);

}
