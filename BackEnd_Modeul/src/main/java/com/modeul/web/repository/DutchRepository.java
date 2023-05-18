package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;

@Mapper
public interface DutchRepository {
    List<DutchView> findViewBymemberId(Long memberId, 
                                        int month,
                                        String orderField,
                                        String orderDir,
                                        Integer size, 
                                        Integer offset);
    
    /* 회원이 정산한 달만 조회 */
    List<Integer> findViewMonthBymemberId(Long memberId);

    /* 공구상품별 정산에 참여한 회원 조회 */
    List<DutchMemberView> findMemberViewBystuffId(Long stuffId);

    /* 정산 가격만 Dutch 테이블에 insert */
    int insertDutch(Long stuffId, Long memberId, String price);
    
    /* 계좌 정보만 Account 테이블에 insert */
    int insertAccount(String bankName, String number, Long memberId, Long stuffId);

    /* 정산했는지 체크용 정산 내역 목록 조회 */
    List<DutchView> findViewAllBymemberId(Long memberId, String orderField, String orderDir);

    // 더치정보 삭제
    int deleteDutch(Long stuffId);

    Long findCountList(int month, Long memberId);

    DutchView findViewBystuffIdmemberId(Long stuffId, Long memberId, String orderField, String orderDir);
}
