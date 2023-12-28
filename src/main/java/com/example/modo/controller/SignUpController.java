package com.example.modo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.service.SignUpService;

@RestController
public class SignUpController {

	@Autowired
	SignUpService signUpService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody Member member) {
		
		System.out.println(member.toString());
		
		Member findMemberUsername = signUpService.getMember(member.getUsername());
		
		if(findMemberUsername.getUsername() == null) {
			signUpService.signUp(member);
			return new ResponseEntity<>("회원가입 완료!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("이미 가입된 회원입니다!", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/usernameCheck")
	public ResponseEntity<?> usernameCheck(@RequestBody Member member) {
		
		System.out.println(member);
		
		Member usernameCheck = signUpService.getMember(member.getUsername());
		
		if (usernameCheck.getUsername() != null && usernameCheck.getUsername().equals(member.getUsername())) {
			return new ResponseEntity<>("중복된 아이디입니다!", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("사용 가능한 아이디입니다!", HttpStatus.OK);
		}
	}
	
	@PostMapping("/nicknameCheck")
	public ResponseEntity<?> nicknameCheck(@RequestBody Member member) {
		
		Member nicknameCheck = signUpService.getMember(member.getNickname());
		
		if (nicknameCheck.getNickname() != null && nicknameCheck.getNickname().equals(member.getNickname())) {
			return new ResponseEntity<>("중복된 닉네임입니다!", HttpStatus.BAD_REQUEST);
		} else {
			return new ResponseEntity<>("사용 가능한 닉네임입니다!", HttpStatus.OK);
		}
	}
	
}
