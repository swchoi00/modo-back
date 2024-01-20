package com.example.modo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.service.MyPageService;

@RestController
public class MyPageController {
	
	@Autowired
	private MyPageService myPageService;

	@PostMapping("/updateInfo")
	public ResponseEntity<?> updateInfo (@RequestBody Member member) {
		
		myPageService.updateInfo(member);
		
		return new ResponseEntity<String>("수정이 완료되었습니다!", HttpStatus.OK);
		
	}
}
