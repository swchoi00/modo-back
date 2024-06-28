package com.example.modo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.service.MemberService;

@RestController
public class GoogleLoginController {

	@Value("${google.default.password}")
	private String googlePassword;
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/oauth/google")
	public ResponseEntity<?> googleLogin (@RequestBody Map<String, String> accessToken) {
		
		Member member = memberService.googleLogin(accessToken.get("accessToken"));
		
		Member findMember = memberService.checkMember(member.getUsername());
		
		if(findMember.getUsername() == null) {
//			memberService.insertMember(member); 기존 바로 회원가입 시키던 코드
			memberService.socialJoin(member, "google");
			
			return new ResponseEntity<>(member, HttpStatus.OK);
		}
		
		return memberService.getResponseEntity(member.getUsername(), googlePassword);
		
	}
	
	
}
