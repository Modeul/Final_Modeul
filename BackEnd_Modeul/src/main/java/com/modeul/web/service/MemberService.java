package com.modeul.web.service;

import com.modeul.web.entity.Member;

public interface MemberService {
	
	String login(Member member);

	int addMember(Member member);
	
	Boolean checkUid(String uid);

	String checkPwd(Member member);

	Boolean checkEmail(String email);

	Boolean checkNickname(String nickname);

	int updateMember(Member member);

	Member getMember(int id);

	int deleteMember(Member member);

	void updateImg(Member member);
	
}

