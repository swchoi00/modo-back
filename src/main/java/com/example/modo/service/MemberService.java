package com.example.modo.service;


import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.modo.domain.Member;
import com.example.modo.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	public ResponseEntity<?> getResponseEntity(String username, String password) {

		UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(username, password);

		Authentication auth = authenticationManager.authenticate(upaToken);
		
		String jwt = jwtService.getToken(auth.getName());

		Member member = memberRepository.findByUsername(username).get();
		
		MultiValueMap<String, Member> body = new LinkedMultiValueMap<>();

		body.add("member", member);

		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").body(body);
	}
	

	
	
}
