package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Comm;
import com.example.modo.domain.FAQ;
import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.domain.Notice;
import com.example.modo.service.AdminService;

@RestController
public class AdminCotroller {
	
	@Autowired
	AdminService adminService;
	
	// ---------- 회원관리
	// 회원 목록
	@GetMapping("/getMemberList")
	public  ResponseEntity<?> getMemberList() {
		List<Member> memberList = adminService.getMemberList();
		
		return new ResponseEntity<>(memberList, HttpStatus.OK);		
	}
	// 회원 삭제
	
	
}