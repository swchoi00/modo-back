package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.MoimReply;
import com.example.modo.service.MoimReplyService;

@RestController
public class MoimReplyController {

	@Autowired
	private MoimReplyService moimReplyService;
	
	// 모임에 해당하는 댓글 리스트 불러오기
	@GetMapping("/moimReply/{id}/list")
	public ResponseEntity<List<MoimReply>> getMoimReply(@PathVariable Long id) {
		
		List<MoimReply> moimReply = moimReplyService.getMoimReplyById(id);
		
		return new ResponseEntity<>(moimReply, HttpStatus.OK);
		
	}
	
	// 댓글작성, ★모임id 받아와야함
	@PostMapping("/moimReply/{id}")
	public ResponseEntity<?> insertMoimReply(@PathVariable Long id, @RequestBody MoimReply moimReply) {
		
		moimReplyService.insertMoimReply(id, moimReply);
		
		return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/moimReply{rno}")
	public ResponseEntity<?> deleteMoimReply(@PathVariable Long rno) {
		
		moimReplyService.deleteMoimReply(rno);
		
		return new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK);
		
	}
	
	@PutMapping("/moimReply_update/{rno}")
	public ResponseEntity<?> updateMoimReply(@PathVariable Long rno, @RequestBody MoimReply moimReply) {
		
		moimReplyService.updateMoimReply(rno, moimReply);
		
		return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
		
	}
	
}
