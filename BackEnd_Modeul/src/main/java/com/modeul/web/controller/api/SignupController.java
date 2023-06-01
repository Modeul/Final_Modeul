package com.modeul.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Member;
import com.modeul.web.service.MailService;
import com.modeul.web.service.MemberService;

@RestController("apiSignupController")
@RequestMapping("/api/signup")
public class SignupController {
	
	@Autowired
	private MemberService service;
	
	@Autowired
	private MailService mailService;
	
	@PostMapping
	public int addMember(@RequestBody Member member) {
		
		int memberCount = service.addMember(member);
		System.out.println(member);
		return memberCount;
	}
	@PostMapping("google")
	public int addGoogleMember(@RequestBody Member member) {
		
		int memberCount = service.addGoogleMember(member);
		System.out.println(member);
		return memberCount;
	}

	// 이메일 인증 (RequestParam)
	@PostMapping("mailConfirm")
	@ResponseBody
	public String mailConfirm(@RequestParam("email") String email) throws Exception{
		String code = mailService.sendConfirmMessage(email);
		System.out.println("인증코드 : " + code);
		return code;
	}
	
	@GetMapping("checkUid")
	public Boolean checkUid (String uid) {

		Boolean chk = service.checkUid(uid);
		
		return chk;
	}
	
	@GetMapping("checkEmail")
	public Boolean checkEmail (String email) {
		
		Boolean chk = service.checkEmail(email);
		
		return chk;
	}
	
	@GetMapping("checkNickname")
	public Boolean checkNickname (String nickname) {
		
		Boolean chk = service.checkNickname(nickname);
		
		return chk;
	}
}
