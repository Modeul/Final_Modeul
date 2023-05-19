package com.modeul.web.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Member;
import com.modeul.web.entity.MemberImage;

@Mapper
public interface MemberRepository {


	int insert(Member member);
	
	Member getbyId(int id);

	List<Member> findAll();

	String getbyUid(String uid);

	Member getPwdByUid(String uid);

	String getByName(String name);

	String getByEmail(String email);

	String getByNickname(String nickname);

	int update(Member member);

	int updatePwd(Member member);

	int delete(Member member);

	String getEmailByUid(Member member);

	void updateImg(MemberImage memberImage);

	String getNameByEmail(Member member);

	String getUid(String name,String email);

    Member getMemberByEmail(String email);
}
