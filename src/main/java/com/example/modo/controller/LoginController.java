package com.example.modo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.service.MemberService;

@RestController
public class LoginController {
	
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member member) {
		
		String username = member.getUsername();
		String password = member.getPassword();
		
		return memberService.getResponseEntity(username, password);
		
	}
	
}
