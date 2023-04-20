package com.modeul.web.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.modeul.web.entity.Member;
import com.modeul.web.repository.MemberRepository;
import com.modeul.web.security.dto.ModeulDetails;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ModeulUserDetailsService implements UserDetailsService{
	
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
		
		log.info(uid);
		
		Member member = memberRepository.findByUid(uid);
		
		String role = "ROLE_"+ Integer.toString(member.getLevel());
		
		log.info(member);
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		
		UserDetails modeulDetails = new ModeulDetails(
				member.getUid(),
				member.getPwd(),
				true,
				true,
				true,
				true,
				authorities
				);
		
		return modeulDetails;
	}

}
