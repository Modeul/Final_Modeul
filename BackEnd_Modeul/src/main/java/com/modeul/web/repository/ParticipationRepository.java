package com.modeul.web.repository;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Participation;

@Mapper
public interface ParticipationRepository {
    int insert(Participation participation);
}
