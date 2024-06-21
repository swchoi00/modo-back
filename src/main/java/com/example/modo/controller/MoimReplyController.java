package com.example.modo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	// 모임에 해당하는 댓글 리스트 불러오기 (↓ 이 방법으로 하면, 댓글이 최초 한 개값만 제대로 보내짐)
//	@GetMapping("/moimReply/{id}/list")
//	public ResponseEntity<List<MoimReply>> getMoimReply(@PathVariable Long id) {
//		
//		List<MoimReply> moimReply = moimReplyService.getMoimReplyById(id);
//		return new ResponseEntity<>(moimReply, HttpStatus.OK);
//		
//	}
	// 이건 gemini(AI)가 알려준 방법 , 이렇게 하면 댓글값은 재대로 받아짐...
	// 근데 처음 댓글 쓴 글쓴이만 계속 댓글 쓸 수 있고, 다른아이디는 댓글 못써
	@GetMapping("/moimReply/{id}/list")
	public ResponseEntity<List<Map<String, Object>>> getMoimReply(@PathVariable Long id) {
	    List<MoimReply> moimReplyList = moimReplyService.getMoimReplyById(id);
	    List<Map<String, Object>> replyList = moimReplyList.stream()
	            .map(reply -> {
	                Map<String, Object> replyData = new HashMap<>();
	                replyData.put("rno", reply.getRno());
	                replyData.put("content", reply.getContent());
	                replyData.put("createDate", reply.getCreateDate().toString());
	                replyData.put("moim", reply.getMoim()); // Moim 객체를 그대로 포함
	                replyData.put("moimMember", reply.getMoimMember()); // MoimMember 객체를 그대로 포함
	                return replyData;
	            })
	            .collect(Collectors.toList());
	    return new ResponseEntity<>(replyList, HttpStatus.OK);
	}
	
	// 댓글작성, ★모임id 받아와야함
	@PostMapping("/moimReply/{id}")
	public ResponseEntity<?> insertMoimReply(@PathVariable Long id, @RequestBody MoimReply moimReply) {

		moimReplyService.insertMoimReply(id, moimReply);
		
		return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/moimReply/{rno}")
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
