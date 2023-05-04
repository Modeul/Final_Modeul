package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.DutchView;

@Mapper
public interface DutchRepository {
    List<DutchView> findViewBymemberId(Long memberId, 
                                        String orderField,
                                        String orderDir,
                                        Integer size, 
                                        Integer offset);
}
