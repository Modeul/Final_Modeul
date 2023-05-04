package com.modeul.web.controller.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

	// @PostMapping("login")
	// public String login(@RequestBody Member member) {
	// 	String result = memberService.login(member);
	// 	return result;
	// }

	@PostMapping("login")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Member member) {
		Map<String, Object> dto = new HashMap<>();
        dto.put("result", null);

        // 이 정보를 서비스 레이어에서 전달 해 줘야 한다.
        if (memberService.isValid(member)){
			String uid = member.getUid();
            Member loginMember = memberService.getMemberByUid(uid);
            dto.put("result", loginMember);
        }

		return new ResponseEntity<Map<String, Object>>(dto, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public Member getMember(@PathVariable("id") int id){

		return memberService.getMember(id);
	}

	@GetMapping("findUid")
	public String findUid(String name, String email){
		String uid = memberService.findUid(name,email);
		return uid;
	}

	@PutMapping("update")
	public int editMember(@RequestBody Member member){

		return memberService.updateMember(member);
	}

	@PutMapping("updatePwd")
	public int editMemberPwd(@RequestBody Member member){

		return memberService.changePwdByUid(member);
	}

	@DeleteMapping("delete")
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

	@GetMapping("checkName")
	public Boolean checkName (String name) {

		Boolean chk = memberService.checkName(name);
		
		return chk;
	}

	@PostMapping("checkEmail")
	public Boolean checkEmailByUid (@RequestBody Member member){

		Boolean chk = memberService.checkEmailByUid(member);
		
		return chk;
	}

	@PostMapping("checkEmailByName")
	public Boolean checkEmailByName (@RequestBody Member member){

		Boolean chk = memberService.checkEmailByName(member);
		
		return chk;
	}

	@PostMapping("updateImage")
	public String updateImage(@RequestParam("id") long id, List<MultipartFile> imgs){

		memberService.updateImg(id, imgs);

		return "ok";
	}

	@GetMapping("list")
	public List<Member> getMemberList(){

		return memberService.getMemberList();
	}
}
