package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;

@Mapper
public interface ParticipationRepository {
    int insert(Participation participation);

    List<ParticipationView> findByMemberId(Long memberId, 
                                            Long categoryId, 
                                            String orderField, 
                                            String orderDir,
                                            Integer size, 
                                            Integer offset);

    List<ParticipationMemberView> findMemberBystuffId(Long stuffId);
}
