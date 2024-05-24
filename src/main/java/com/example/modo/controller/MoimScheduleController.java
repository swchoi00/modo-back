package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.MoimSchedule;
import com.example.modo.service.MoimScheduleService;

@RestController
public class MoimScheduleController {

	@Autowired
	MoimScheduleService moimScheduleService;
	
	
	// 일단 모임 id는 URL로 따로 받아오게 설정
	@PostMapping("/createMoimSchedule/{id}")
	public ResponseEntity<?> createMoimSchedule(@PathVariable Long id, @RequestBody MoimSchedule moimSchedule) {
		
		moimScheduleService.insertMoimSchedule(id, moimSchedule);
		
		return new ResponseEntity<>("모임일정 생성 완료", HttpStatus.OK);
		
	}
	
	@GetMapping("/getMoimSchedule/{id}/list")
	public ResponseEntity<List<MoimSchedule>> getMoimSchedules(@PathVariable Long id) {
		
		List<MoimSchedule> moimSchedule = moimScheduleService.getMoimSchedules(id);
		
		return new ResponseEntity<>(moimSchedule, HttpStatus.OK);
	}
	
	@GetMapping("/getMoimScheduleDetail/{no}")
	public ResponseEntity<?> getMoimScheduleDetail(@PathVariable Long no) {
		
		MoimSchedule moimScheduleInfo = moimScheduleService.getMoimScheduleDetail(no);
		
		return new ResponseEntity<>(moimScheduleInfo, HttpStatus.OK);
	}
}
