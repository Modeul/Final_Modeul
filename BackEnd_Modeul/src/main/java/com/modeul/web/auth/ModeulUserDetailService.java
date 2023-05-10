package com.modeul.web.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.modeul.web.entity.Member;
import com.modeul.web.repository.MemberRepository;

// @Service
public class ModeulUserDetailService implements UserDetailsService {
    
    @Autowired
    private MemberRepository repository;

    @Override
    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Member member = repository.getPwdByUid(uid);
        ModeulUserDetails user = new ModeulUserDetails();

        user.setId(member.getId());
        user.setUid(member.getUid());
        user.setPwd(member.getPwd());
        user.setName(member.getName());
        user.setEmail(member.getEmail());
        user.setNickname(member.getNickname());
        user.setLevel(member.getLevel());
        user.setImage(member.getImage());
        user.setAuthorities(authorities);

        return user;
    }
}
