package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;

@Mapper
public interface ParticipationRepository {
    int insert(Participation participation);
    List<ParticipationMemberView> findViewAllMemberListbyStuffId(Long stuffId);
}
