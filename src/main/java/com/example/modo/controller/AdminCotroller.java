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
    @DeleteMapping("/deleteMemberList")
    public ResponseEntity<?> deleteMembereList(@RequestBody List<Long> list) {
        try {
        	adminService.deleteMember(list);
            return ResponseEntity.ok("해당 회원을 삭제 완료했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1:1문의 삭제 실패");
        }
    }
	
}