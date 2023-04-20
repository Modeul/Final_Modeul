package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.service.ParticipationService;

@RestController
@RequestMapping("api")
public class ParticipationController {
    
    @Autowired
    private ParticipationService participationService;

    @PostMapping("/participation")
    public String addParticipation(@RequestBody Participation participation){
       participationService.addParticipation(participation);

       return "ok";
    }

    @GetMapping("/participations/{memberId}")
    public Map<String, Object> getList(@PathVariable("memberId") Long memberId){

        Map<String, Object> dataList = new HashMap<>();
        // dataList.put();
        return dataList;
    }

    @GetMapping("/chat/{stuffId}")
    public List<ParticipationMemberView> getViewMemberListbystuffId(@PathVariable("stuffId") Long stuffId){
        List<ParticipationMemberView> memberList = participationService.getViewAllbwyStuffId(stuffId);
        System.out.println(memberList);
        return memberList;
    }
    
}
