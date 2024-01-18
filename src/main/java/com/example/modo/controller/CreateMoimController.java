package com.example.modo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Moim;
import com.example.modo.service.MoimService;

@RestController
public class CreateMoimController {
	
	@Autowired
	MoimService moimService;
	
	@PostMapping("/moimnameCheck")
	public ResponseEntity<?> moimnameCheck(@RequestBody Moim moim) {
		
		System.out.println(moim);
		
		Moim moimnameCheck = moimService.getMoim(moim.getMoimname());
		
		if (moimnameCheck.getMoimname() != null && moimnameCheck.getMoimname().equals(moim.getMoimname())) {
			return new ResponseEntity<>("중복된 모임 이름입니다!", HttpStatus.BAD_REQUEST);
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
