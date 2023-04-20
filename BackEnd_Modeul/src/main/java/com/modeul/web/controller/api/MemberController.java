package com.modeul.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Member;
import com.modeul.web.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@PutMapping("update")
	public int editMember(@RequestBody Member member){

		return memberService.updateMember(member);
	}
	
}
