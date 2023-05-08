package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.modeul.web.entity.Account;
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

    // int insertDutch(@Param("stuffId") Long stuffId, @Param("memberId") Long memberId, @Param("price") Integer price);
    int insertDutch(Long stuffId, Long memberId, Integer price);
    
    // int insertAccount(Account account);
    int insertAccount(String bankName, String number, Long memberId);
}
