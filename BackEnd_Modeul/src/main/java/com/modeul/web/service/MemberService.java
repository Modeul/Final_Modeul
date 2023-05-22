package com.modeul.web.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.modeul.web.entity.Member;

public interface MemberService {

	String login(Member member);

	Boolean isValid(Member member);

	Member getMemberByUid(String uid);

	int addMember(Member member);

	int addGoogleMember(Member member);

	int changePwdByUid(Member member);

	Boolean checkUid(String uid);

	Boolean checkName(String name);

	Boolean checkPwd(Member member);

	Boolean checkEmail(String email);

	Boolean checkNickname(String nickname);

	int updateMember(Member member);

	Member getMember(int id);

	int deleteMember(Member member);

	void updateImg(long id, List<MultipartFile> imgs);

	Boolean checkEmailByUid(Member member);

	Boolean checkEmailByName(Member member);

	String findUid(String name, String email);

	List<Member> getMemberList();

    Member getMemberByEmail(String email);

	String createUid();

	String createPwd();
}
