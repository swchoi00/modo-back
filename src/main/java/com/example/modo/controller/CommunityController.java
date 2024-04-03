package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Comm;
import com.example.modo.service.CommunityService;

@RestController
public class CommunityController {

	@Autowired
	CommunityService communityService;
	
	// 글 작성
	@PostMapping("/comm_insert")
	public ResponseEntity<?> insertPost(@RequestBody Comm comm) {
		
		communityService.insertPost(comm);
		
		// 작성완료시 문구 조율
		return new ResponseEntity<>("게시글 작성 완료!", HttpStatus.OK);
	}
	
	@GetMapping("/comm_getList")
	public ResponseEntity<?> getCommList() {
		
		List<Comm> commList = communityService.getCommList();
		
		return new ResponseEntity<>(commList, HttpStatus.OK);
				
		
	}
	
	
	
}
