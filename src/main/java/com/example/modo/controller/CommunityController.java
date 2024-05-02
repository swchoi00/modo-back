package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Comm;
import com.example.modo.service.CommunityService;
import com.example.modo.service.MemberService;

@RestController
public class CommunityController {

	@Autowired
	CommunityService communityService;
	
	@Autowired
	MemberService memberService;
	
	// 글 작성
	@PostMapping("/comm_insert")
	public ResponseEntity<?> insertPost(@RequestBody Comm comm) {
		
		communityService.insertPost(comm);
		
		// 작성완료시 문구 조율
		return new ResponseEntity<>("게시글 작성 완료!", HttpStatus.OK);
	}
	

	
	// 글 출력
	@GetMapping("/comm_getList")
	public ResponseEntity<?> getCommList() {
		
		List<Comm> commList = communityService.getCommList();
		
		return new ResponseEntity<>(commList, HttpStatus.OK);
				
		
	}
	
	// 닉네임 불러오기
	@GetMapping("/comm_getNickname")
	public ResponseEntity<?> getNickName(@RequestBody String username) {
		
		String nickname = memberService.getNickname(username);
		
		return new ResponseEntity<>(nickname, HttpStatus.OK);
		
	}
	
	// 게시글 상세보기
	@GetMapping("/comm/{id}")
	public ResponseEntity<?> getComm(@PathVariable Long id) {
	      
	      Comm comm = communityService.getComm(id);
	      
	      return new ResponseEntity<>(comm, HttpStatus.OK);
	   }
	
	// 게시글 삭제
	@DeleteMapping("/comm_delete/{id}")
	public ResponseEntity<?> deleteComm(@PathVariable Long id) {
		
		communityService.deleteComm(id);
		
		return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
		
	}
	
	// 게시글 수정
	@PostMapping("/comm_update/{id}")
	public ResponseEntity<?> updateComm(@PathVariable Long id, @RequestBody Comm comm ) {
		
		communityService.updateComm(id, comm);
		
		return new ResponseEntity<>("게시글 수정 완료", HttpStatus.OK);
	}
	
	
	
}
