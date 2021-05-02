package com.example.bookreservationserver.user.service;

import com.example.bookreservationserver.user.domain.aggregate.User;
import com.example.bookreservationserver.user.domain.repository.UserEntityRepository;
import com.example.bookreservationserver.user.dto.JoinRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JoinService {
    @Autowired
    private UserEntityRepository userEntityRepository;

    @Transactional
    public void join(JoinRequest joinRequest){
        checkDuplicateId(joinRequest.getEmail());
        saveAsNewUser(joinRequest);
    }

    private void saveAsNewUser(JoinRequest joinRequest) {
        User user = new User(joinRequest);
        userEntityRepository.save(user);
    }

    private void checkDuplicateId(String email){
        if(userEntityRepository.countByEmail(email) > 0)
            throw new IllegalArgumentException("duplicated email.");
    }
}