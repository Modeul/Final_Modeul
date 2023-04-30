package com.modeul.web.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.modeul.web.entity.Member;
import com.modeul.web.service.MailService;
import com.modeul.web.service.MemberService;

@RestController
@RequestMapping("/api/member")
public class MemberController {

	@Autowired
	MemberService memberService;

	@Autowired
	MailService mailService;

	@PostMapping("login")
	public String login(@RequestBody Member member) {
		String result = memberService.login(member);
		return result;
	}

	@GetMapping("{id}")
	public Member getMember(@PathVariable("id") int id){

		return memberService.getMember(id);
	}

	@PutMapping("update")
	public int editMember(@RequestBody Member member){

		return memberService.updateMember(member);
	}

	@PutMapping("updatePwd")
	public int editMemberPwd(@RequestBody Member member){

		return memberService.changePwdByUid(member);
	}

	@PutMapping("delete")
	public int deleteMember(@RequestBody Member member){

		return memberService.deleteMember(member);
	}
	
	// 임시 비밀번호 발송
	@PostMapping("sendTempPwd")
	@ResponseBody
	public String sendTempPwd(@RequestParam("email") String email) throws Exception{
		String tempPwd = mailService.sendTempPwdMessage(email);
		System.out.println("메일주소 : " + email);
		System.out.println("임시 비밀번호 : " + tempPwd);
		return tempPwd;
	}

	@PostMapping("checkpwd")
	public Boolean checkPwd(@RequestBody Member member) {

		if (memberService.checkPwd(member) == "ok")
			return true;
		else 
			return false;
	}

	@GetMapping("checkUid")
	public Boolean checkUid (String uid) {

		Boolean chk = memberService.checkUid(uid);
		
		return chk;
	}

	@PostMapping("checkEmail")
	public Boolean checkEmailByUid (@RequestBody Member member){

		Boolean chk = memberService.checkEmailByUid(member);
		
		return chk;
	}
}
