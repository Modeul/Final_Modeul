package com.modeul.web.service;

import java.util.List;

import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;

public interface DutchService {
    List<DutchView> getViewBymemberId(Long memberId, int page, int month);

    List<DutchMemberView> getMemberViewBystuffId(Long stuffId);

    List<Integer> getViewMonthBymemberId(Long memberId);
}
