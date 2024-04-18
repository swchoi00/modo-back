package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.service.MemberService;
import com.example.modo.service.MoimService;

@RestController
public class CreateMoimController {
	
	@Autowired
	MoimService moimService;
	
	// ■■1■■ 좋아요 모임 관리를 위해 추가
	@Autowired
	MemberService memberService;
	
	
	@PostMapping("/upDateLikedMoim")
	public ResponseEntity<?> upDateLikedMoim(@RequestBody Member member) {
	    
		Member updateMember = memberService.updateLikedMoims(member);
		
		return new ResponseEntity<>(updateMember,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/moimList")
	public ResponseEntity<?> getMoimList() {
		
		List<Moim> moimList = moimService.getMoimList();
		
		return new ResponseEntity<>(moimList, HttpStatus.OK);
	}
	
	
	//추후 모임일정, 게시판, 모임 멤버 등도 받아와야함 
	@GetMapping("/moimInfo/{id}")
	public ResponseEntity<?> getMoimInfo(@PathVariable Long id) {
		Moim moimInfo = moimService.getMoimInfo(id);
		
		return new ResponseEntity<>(moimInfo, HttpStatus.OK);
	}
	
	
	@PostMapping("/moimnameCheck")
	public ResponseEntity<?> moimnameCheck(@RequestBody Moim moim) {
		
		System.out.println(moim);
		
		Moim moimnameCheck = moimService.getMoim(moim.getMoimname());
		
		if (moimnameCheck.getMoimname() != null && moimnameCheck.getMoimname().equals(moim.getMoimname())) {
			return new ResponseEntity<>("중복된 모임 이름입니다!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("사용 가능한 모임 이름입니다!", HttpStatus.OK);
		}
		
	}
	
	
	@PostMapping("/createMoim")
	public ResponseEntity<?> createMoim(@RequestBody Moim moim) {
		
		moimService.insertMoim(moim);
		
		return new ResponseEntity<>("모임 생성이 완료되었습니다!", HttpStatus.OK);
		
	}
	
}
