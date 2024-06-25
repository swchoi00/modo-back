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
	
	
	// ---------- 모임관리
	// 모임 목록
	@GetMapping("/getMoimList")
	public ResponseEntity<?> getMoimList() {
		
		List<Moim> moimList = adminService.getMoimList();
		
		return new ResponseEntity<>(moimList, HttpStatus.OK);
	}
	// 모임 삭제

	
	// ---------- 커뮤니티관리
	// 커뮤니티 목록
	@GetMapping("/getCommunityList")
	public ResponseEntity<?> getCommList() {
		
		List<Comm> commList = adminService.getCommList();
		
		return new ResponseEntity<>(commList, HttpStatus.OK);	
	}
	// 커뮤니티 삭제
	
	
	// ---------- FAQ관리
	// FAQ 목록
	@GetMapping("/getFAQList")
	public ResponseEntity<?> getFaqList() {
		
		List<FAQ> faqList = adminService.getFaqList();
		
		return new ResponseEntity<>(faqList, HttpStatus.OK);
	}
	// 해당 FAQ 보기
	// FAQ 글작성/수정
	// FAQ 삭제
	
	
	// ---------- 1:1문의관리
	// 1:1문의 목록
	// 해당 1:1문의 보기
	// 1:1문의 답변작성/수정
	// 1:1문의 삭제
	
	// ---------- 공지사항관리
	// 공지사항 목록
	@GetMapping("/getNoticeList")
	public ResponseEntity<?> getNoticeList() {
		
		List<Notice> noticeList = adminService.getNoticeList();
		
		return new ResponseEntity<>(noticeList, HttpStatus.OK);
	}
	// 해당 공지사항 보기
	// 공지사항 글작성/수정
	// 공지사항 삭제
	
}
