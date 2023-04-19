package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;

public interface ParticipationService {
    int addParticipation(Participation participation);

    List<ParticipationView> getByMemberId(Long memberId);
    List<ParticipationMemberView> getMemberBystuffId(Long stuffId);
}
