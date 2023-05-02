package com.modeul.web.repository;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Image;
import com.modeul.web.entity.Member;

@Mapper
public interface MemberRepository {


	int insert(Member member);
	
	Member getbyId(int id);

	String getbyUid(String uid);

	Member getPwdByUid(String uid);

	String getByName(String name);

	String getByEmail(String email);

	String getByNickname(String nickname);

	int update(Member member);

	int updatePwd(Member member);

    int delete(Member member);

	String getEmailByUid(Member member);

	void updateImg(Member member);

	String getEmailByName(Member member);

	String getUid(String name,String email);
}
