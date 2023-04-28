package com.modeul.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Member;
import com.modeul.web.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int addMember(Member member) {
		String encodedPassword = passwordEncoder.encode(member.getPwd());
		member.setPwd(encodedPassword);

		return repository.insert(member);
	}

	@Override
	public String login(Member member) {
		Member loginMember = repository.getPwdByUid(member.getUid());
		if (loginMember == null) {
			return "해당 아이디가 없습니다.";
		}
		if (!passwordEncoder.matches(member.getPwd(), loginMember.getPwd())) {
			return "비밀번호가 일치하지 않습니다.";
		}
		return null;
	}

	@Override
	public String checkPwd(Member loginMember) {
		Member member = repository.getPwdByUid(loginMember.getUid());
		if (passwordEncoder.matches(loginMember.getPwd(), member.getPwd()) == false) {
			return "비밀번호가 일치하지 않습니다.";
		} else
			return "ok";
	}
	
	@Override
	public Boolean checkUid(String uid) {
		String getUid = repository.getbyUid(uid);
		Boolean result = (getUid != null) ? false : true;

		return result;
	}

	@Override
	public Boolean checkEmail(String email) {
		String getEmail = repository.getByEmail(email);
		Boolean result = (getEmail != null) ? false : true;

		return result;
	}

	@Override
	public Boolean checkNickname(String nickname) {
		String getNickname = repository.getByNickname(nickname);
		Boolean result = (getNickname != null) ? false : true;

		return result;
	}

	@Override
	public int updateMember(Member member) {

		return repository.update(member);
	}

	@Override
	public Member getMember(int id) {
		return repository.getbyId(id);
	}

	@Override
	public int deleteMember(Member member) {
		return repository.delete(member);
	}

	@Override
	public void updateImg(Member member) {
		
	}


}
