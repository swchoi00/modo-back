package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Comm;
import com.example.modo.domain.FAQ;
import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.domain.Notice;
import com.example.modo.service.AdminService;
import com.example.modo.service.MemberService;
import com.example.modo.service.MoimService;

@RestController
public class AdminCotroller {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MoimService moimService;
	
	// ---------- 회원관리	
	// 관리자 멤버 리스트 출력
		@GetMapping("/getMemberList")
		public ResponseEntity<?> getMemberList() {
			
			List<Member> memberLists = memberService.getMemberList();
			
			return new ResponseEntity<>(memberLists, HttpStatus.OK);
			
			
		}
		
		// 관리자 멤버 삭제
		@DeleteMapping("/deleteMemberList")
		public ResponseEntity<?> deleteMemberList(@RequestBody List<Long> memberIds) {
			
			memberService.deleteMembersByIds(memberIds);
			
			return new ResponseEntity<>("선택한 멤버 삭제완료", HttpStatus.OK);
			
		}
		
		// 관리자 모임 삭제
		@DeleteMapping("/deleteMoimList")
		public ResponseEntity<?> deleteMoimList(@RequestBody List<Long> moimIds) {
			
			moimService.deleteMoimsByIds(moimIds);
			
			return new ResponseEntity<>("선택한 모임 삭제완료", HttpStatus.OK);
			
		}
}