package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;

public interface ParticipationService {
    int addParticipation(Participation participation);
    List<ParticipationMemberView> getViewAllbwyStuffId(Long stuffId);
}
