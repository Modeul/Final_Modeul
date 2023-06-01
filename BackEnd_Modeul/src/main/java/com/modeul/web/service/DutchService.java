package com.modeul.web.service;

import java.util.List;
import java.util.Map;

import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;

public interface DutchService {
    List<DutchView> getViewBymemberId(Long memberId, int page, int month);

    List<DutchMemberView> getMemberViewBystuffId(Long stuffId);

    List<Integer> getViewMonthBymemberId(Long memberId);

    // void addAllDutch(Long stuffId, Map<Long, Integer> prices, Account account);
    void addAllDutch(Long stuffId, Map<String, Object> dutch);

    List<DutchView> getViewAllBymemberId(Long memberId);

    int removeDutch(Long stuffId);

    Long getListCount(Long memberId, int page, int month);

    DutchView getViewBystuffIdmemberId(Long stuffId, Long memberId);
}
