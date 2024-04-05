package com.example.modo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.example.modo.domain.Member;
import com.example.modo.repository.MemberRepository;

@Service
public class MyPageService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void updateInfo(Member member) {
		
		Member originalMember = memberRepository.findById(member.getId()).orElse(null);
		System.out.println("원래 정보 : " + originalMember);
		
		if(member.getPassword() == null || member.getPassword().isEmpty()) {
			originalMember.setUsername(member.getUsername());
			originalMember.setNickname(member.getNickname());
		} else {
			originalMember.setUsername(member.getUsername());
			originalMember.setNickname(member.getNickname());
			originalMember.setPassword(passwordEncoder.encode(member.getPassword()));			
		}
		
		memberRepository.save(originalMember);
		
		
	}
	
}
