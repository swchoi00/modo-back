package com.example.modo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.domain.Reply;
import com.example.modo.service.ReplyService;

@RestController
public class ReplyController {

	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/reply/{id}/list")
	public ResponseEntity<List<Reply>> getReply(@PathVariable Long id) {
		
		List<Reply> reply = replyService.getReplyById(id);
		
		return new ResponseEntity<>(reply , HttpStatus.OK);
	}
	
	@PostMapping("/reply/{id}")
	public ResponseEntity<?> insertReply(@PathVariable Long id, @RequestBody Reply reply, HttpSession session) {
		
		Member member = (Member)session.getAttribute("principal");
		
		System.out.println(member);
		
		replyService.insertReply(id, reply, member);
		
		return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
			
	}
	
	@DeleteMapping("/reply/{no}")
	public ResponseEntity<?> deleteReply(@PathVariable Long no) {
		
		replyService.deleteReply(no);
		
		return new ResponseEntity<>("댓글 삭제 완료", HttpStatus.OK);
		
	}
	
	@PutMapping("/reply")
	public @ResponseBody ResponseEntity<?> updateReply(@RequestBody Reply reply) {
		
		replyService.updateReply(reply);
		
		return new ResponseEntity<>(reply, HttpStatus.OK);
		
	}
	
}
