package com.example.modo.controller;

import java.util.Map;

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
	public ResponseEntity<?> moimnameCheck(@RequestBody Map<String, String> requestData) {
//		requestData의 key, value값에 맞춰서 받고 아래와 같이 원하는 key의 값을 뽑아낼 수 있음
//		프론트에선 이렇게 보냄, axiosInstance.post('/moimnameCheck',{ moimname: addMoimInfo.moimName })
		
		String moimname = requestData.get("moimname");
		String moimnameCheck = (moimService.getMoim(moimname)).getMoimname();

		if (moimnameCheck != null && moimnameCheck.equals(moimname)) {
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
