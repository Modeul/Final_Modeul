package com.modeul.web.repository;

import org.apache.ibatis.annotations.Mapper;

import com.modeul.web.entity.Member;

@Mapper
public interface MemberRepository {


	int insert(Member member);
	
	Member getbyId(int id);

	String getbyUid(String uid);

	String getByEmail(String email);

	String getByNickname(String nickname);

	int update(Member member);

    int delete(Member member);
}
