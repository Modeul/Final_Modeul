package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        @RequestParam(name="p", defaultValue = "1") int page){

        List<DutchView> list = service.getViewBymemberId(memberId, page);

        Map<String, Object> dataList = new HashMap<>();
        dataList.put("list",list);

        return dataList;
    }
}
