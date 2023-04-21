package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;
import com.modeul.web.repository.ParticipationRepository;

@Service
public class ParticipationServiceImpl implements ParticipationService {

    @Autowired
    ParticipationRepository repository;

    @Override
    public int addParticipation(Participation participation) {
        return repository.insert(participation);
    }

    @Override
    public List<ParticipationView> getByMemberId(Long memberId, Long categoryId, int page) {

        int size = page * 7;

        return repository.findByMemberId(memberId, categoryId, "participation_date", "desc", size, 0);
    }

    @Override
    public List<ParticipationMemberView> getMemberBystuffId(Long stuffId) {
        return repository.findMemberBystuffId(stuffId);
    }

    @Override
    public int cancelParticipation(Long stuffId, Long memberId) {
        return repository.delete(stuffId, memberId);
    }

    @Override
    public Long getMemberCountBystuffId(Long stuffId) {
        // Long memberCount = repository.findMemberCountBystuffId(stuffId);

        // if(memberCount == null)
        //     memberCount = 0L;

        return repository.findMemberCountBystuffId(stuffId);
    }

    @Override
    public int getStuffCountBymemberId(Long memberId) {
        return repository.findStuffCountBymemberId(memberId);
    }

    
}
