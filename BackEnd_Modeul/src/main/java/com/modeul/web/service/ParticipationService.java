package com.modeul.web.service;

import java.util.List;
import java.util.Map;

import com.modeul.web.entity.Message;
import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;

public interface ParticipationService {
    int addParticipation(Participation participation);

    List<ParticipationView> getByMemberId(Long memberId, String orderField, String orderDir, int page);
    List<ParticipationMemberView> getMemberBystuffId(Long stuffId);

    /* 상세 페이지의 참여 취소 기능 */
    int cancelParticipation(Long stuffId, Long memberId);

    /* 공구상품별 참여 멤버 집계 */
    int getMemberCountBystuffId(Long stuffId);

    /* 멤버별 참여 목록 수 집계 */
    int getStuffCountBymemberId(Long memberId);

    ParticipationMemberView getMemberBystuffIdmemberId(Long stuffId, Long memberId);

    void saveCalResultMsg(Message message);

    void calculatedAmount(Long stuffId, Map<Long, Integer> prices);

    void inputAccount(Long stuffId, String account);

    String getAccount(Long leaderId);

    Long getCountList(Long memberId, int page);
}
