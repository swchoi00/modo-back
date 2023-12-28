package com.example.modo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Member;
import com.example.modo.domain.OAuthType;
import com.example.modo.domain.RoleType;
import com.example.modo.repository.MemberRepository;

@Service
public class SignUpService {
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void signUp(Member member) {
		
		member.setRole(RoleType.MEMBER);
		
		if(member.getOauth() == null)
			member.setOauth(OAuthType.MODO);
		
		member.setPassword(passwordEncoder.encode(member.getPassword()));
		
		memberRepository.save(member);
	}
	
	// 회원가입 전 이미 계정이 있는지 확인
	public Member getMember(String username) {
		
		Member member = memberRepository.findByUsername(username).orElseGet(() -> {
			return new Member();
		});
		
		return member;
		
	}
}
