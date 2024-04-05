package com.example.modo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
		
		Member userInfo = memberService.kakaoLogin(accessToken);
		
		Member checkMember = memberService.checkMember(userInfo.getUsername());
		
		if(checkMember.getUsername() == null) {
			memberService.insertMember(userInfo);
		}
		
		return memberService.getResponseEntity(userInfo.getUsername(), kakaoPassword);
		
	}
	
	
}
