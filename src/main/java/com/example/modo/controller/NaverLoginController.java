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

@RestController
public class NaverLoginController {

	@Value("${naver.default.password}")
	private String naverPassword;
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/oauth/naver")
	public ResponseEntity<?> naverLogin(@RequestBody Map<String, String> naverCode) {
		
		System.out.println(naverCode);
		
		String code = naverCode.get("code");
		String state = naverCode.get("state");
		
		
		String accessToken = memberService.getNaverAccessToken(code, state);
		
		Member userInfo = memberService.naverLogin(accessToken);
		
		Member checkMember = memberService.checkMember(userInfo.getUsername());
		
		if(checkMember.getUsername() == null) {
			memberService.insertMember(userInfo);
		}
		
		return memberService.getResponseEntity(userInfo.getUsername(), naverPassword);
		
	}
	
}
