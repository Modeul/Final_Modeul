package com.modeul.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Participation;
import com.modeul.web.repository.ParticipationRepository;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    @Autowired
    ParticipationRepository repository;

    @Override
    public int addParticipation(Participation participation) {
        return repository.insert(participation);
    }
    
}
