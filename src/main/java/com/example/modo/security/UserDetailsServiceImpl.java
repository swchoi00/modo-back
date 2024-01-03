package com.example.modo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Member;
import com.example.modo.repository.MemberRepository;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByUsername(username)
			.orElseThrow(() -> {
				return new UsernameNotFoundException(username + "은 존재하지 않습니다.");
			});
				
		return new UserDetailsImpl(member);
	}

	

}