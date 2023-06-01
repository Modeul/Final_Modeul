package com.modeul.web.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.modeul.web.entity.Message;
import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;

@Mapper
public interface ParticipationRepository {
    int insert(Participation participation);

    /* 멤버별 참여 목록 전체 조회 */
    List<ParticipationView> findByMemberId(Long memberId, 
                                            String orderField, 
                                            String orderDir,
                                            Integer size, 
                                            Integer offset);

    /* 공구상품별 공구상품 글에 참여한 회원 조회 */
    List<ParticipationMemberView> findMemberBystuffId(Long stuffId);

    /* 상세 페이지의 참여 취소 기능 */
    int delete(Long stuffId, Long memberId);

    /* 공구상품별 참여 멤버 집계 */
    Integer findMemberCountBystuffId(Long stuffId);

    /* 멤버별 참여 목록 수 집계 */
    Integer findStuffCountBymemberId(Long memberId);

    ParticipationMemberView findMemberBystuffIdmemberId(Long stuffId, Long memberId);

    void putCalResultMsg(Message message);

    int insertCalculatedAmount(@Param("stuffId") Long stuffId, @Param("memberId") Long memberId, @Param("price") Integer price);

    void insertAccount(@Param("stuffId") Long stuffId, String account);

    String getAccount(Long leaderId);

    Long findCountList(Long memberId);

}
