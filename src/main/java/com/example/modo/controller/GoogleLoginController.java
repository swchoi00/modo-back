package com.example.modo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.domain.OAuthType;
import com.example.modo.domain.RoleType;
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
		System.out.println("멤버 확인 : " + member);
		
		String memberPw = member.getPassword();
		
		Member findMember = memberService.checkMember(member.getUsername());
		System.out.println("checkMember : " + findMember);
		
		
		if(findMember.getUsername() == null) {
			
			return new ResponseEntity<>(member, HttpStatus.OK);
		}
		
		return memberService.getResponseEntity(member.getUsername(), memberPw);
		
	}
	
	@PostMapping("/oauth/join")
	public ResponseEntity<?> SocialJoin (@RequestBody Member member){
		Member checkMember = memberService.checkMember(member.getUsername());
		
		System.out.println("멤버 체크 :" + member);
		
		String memberPw = member.getPassword();
		
		
		if(checkMember.getUsername() == null) {
			memberService.socialJoin(member);
		}
		
		return memberService.getResponseEntity(member.getUsername(), memberPw);
		
	}
	
}
