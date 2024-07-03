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
import com.example.modo.service.SignUpService;

@RestController
public class KakaoLoginController {

	@Value("${kakao.default.password}")
	private String kakaoPassword;
	
	@Autowired
	private SignUpService signUpService;
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/oauth/kakao")
	public ResponseEntity<?> kakaoLogin(@RequestBody Map<String, String> kakaoCode) {
		
		String code = kakaoCode.get("code");
		System.out.println("코드 : " + code);
		
		String accessToken = memberService.getKakaoAccessToken(code);
		System.out.println("액세스토큰 : " + accessToken);
		
		Member member = memberService.kakaoLogin(accessToken);
	
		String memberPw = member.getPassword();
		
		
		Member checkMember = memberService.checkMember(member.getUsername());
		System.out.println("체크멤버 : " + checkMember );
		
		if(checkMember.getUsername() == null) {
			System.out.println("멤버 : " + member);
			return new ResponseEntity<>(member, HttpStatus.OK);
		}
		
		return memberService.getResponseEntity(member.getUsername(), memberPw);
		
	}
	
	
}
