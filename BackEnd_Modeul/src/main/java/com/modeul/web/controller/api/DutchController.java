package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.DutchMemberView;
import com.modeul.web.entity.DutchView;
import com.modeul.web.service.DutchService;

@RestController
@RequestMapping("api")
public class DutchController {
    
    @Autowired
    private DutchService service;

    @GetMapping("dutchs")
    public Map<String, Object> getList(
        @RequestParam Long memberId, 
        @RequestParam(name="p", defaultValue = "1") int page,
        @RequestParam(name="m", defaultValue = "1") int month){

        List<DutchView> list = service.getViewBymemberId(memberId, page, month);
        List<Integer> months = service.getViewMonthBymemberId(memberId);

        Map<String, Object> dataList = new HashMap<>();
        dataList.put("list",list);
        dataList.put("months",months);

        return dataList;
    }

    @GetMapping("dutch/{stuffId}")
    public Map<String, Object> getMemberList(
        @PathVariable("stuffId") Long stuffId){

        List<DutchMemberView> list = service.getMemberViewBystuffId(stuffId);

        Map<String, Object> dataList = new HashMap<>();
        dataList.put("list",list);

        return dataList;
    }

}
