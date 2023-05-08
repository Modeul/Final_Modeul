package com.modeul.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;
import com.modeul.web.repository.DutchRepository;

@Service
public class DutchServiceImpl implements DutchService {

    @Autowired
    private DutchRepository repository;

    @Override
    public List<DutchView> getViewBymemberId(Long memberId, int page, int month) {

        int size = page * 8;

        return repository.findViewBymemberId(memberId, month, "date", "desc", size, 0);
    }

    @Override
    public List<DutchMemberView> getMemberViewBystuffId(Long stuffId) {
        return repository.findMemberViewBystuffId(stuffId);
    }

    @Override
    public List<Integer> getViewMonthBymemberId(Long memberId) {
        return repository.findViewMonthBymemberId(memberId);
    }
    
}
