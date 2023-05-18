package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Participation;
import com.modeul.web.entity.ParticipationMemberView;
import com.modeul.web.entity.ParticipationView;
import com.modeul.web.entity.StuffView;
import com.modeul.web.service.CategoryService;
import com.modeul.web.service.ParticipationService;
import com.modeul.web.service.StuffService;

@RestController
@RequestMapping("api")
public class ParticipationController {

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StuffService stuffService;

    @PostMapping("/participation")
    public String addParticipation(@RequestBody Participation participation) {
        participationService.addParticipation(participation);

        return "ok";
    }

    @GetMapping("/participations")
    public Map<String, Object> getList(
            Long memberId,
            @RequestParam(name = "p", defaultValue = "1") int page,
            @RequestParam(name = "of", required = false) String orderField,
            @RequestParam(name = "od", required = false) String orderDir) {

        List<ParticipationView> list = participationService.getByMemberId(memberId, orderField, orderDir, page);
        int stuffCount = participationService.getStuffCountBymemberId(memberId);
        Long listCount = participationService.getCountList(memberId, page);

        Map<String, Object> dataList = new HashMap<>();
        dataList.put("list", list);
        dataList.put("stuffCount", stuffCount);
        dataList.put("listCount", listCount);

        return dataList;
    }

    // 참여하기 버튼 누르면 실시간 참여 멤버 인원 업데이트를 위해 필요하다.
    @GetMapping("/participation/stuff/{stuffId}")
    public Map<String, Object> get(
            @PathVariable("stuffId") Long stuffId) {

        List<ParticipationMemberView> list = participationService.getMemberBystuffId(stuffId);
        int memberCount = participationService.getMemberCountBystuffId(stuffId);

        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        data.put("memberCount", memberCount);

        return data;
    }

    @DeleteMapping("/participation/{stuffId}/{memberId}")
    public String cancelParticipation(
            @PathVariable("stuffId") Long stuffId,
            @PathVariable("memberId") Long memberId) {

        int cancelParticipationCount = participationService.cancelParticipation(stuffId, memberId);

        System.out.printf("cancelParticipationCount: %d\n", cancelParticipationCount);

        return "cancel ok";
    }

    @GetMapping("/chat/{stuffId}")
    public Map<String, Object> getViewChat(
            @PathVariable("stuffId") Long stuffId) {

        List<ParticipationMemberView> memberList = participationService.getMemberBystuffId(stuffId);
        StuffView stuffView = stuffService.getViewById(stuffId);

        Map<String, Object> dataList = new HashMap<>();
        dataList.put("memberList", memberList);
        dataList.put("stuffView", stuffView);

        System.out.println(memberList);
        return dataList;
    }

    @GetMapping("/chat/{stuffId}/{memberId}")
    public Map<String, Object> getChat(
            @PathVariable("stuffId") Long stuffId,
            @PathVariable("memberId") Long memberId) {

        ParticipationMemberView memberInfo = participationService.getMemberBystuffIdmemberId(stuffId, memberId);

        Map<String, Object> data = new HashMap<>();
        data.put("memberInfo", memberInfo);

        System.out.println(memberInfo);
        return data;
    }
}