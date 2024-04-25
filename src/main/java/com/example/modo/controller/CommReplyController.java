package com.example.modo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.CommReply;
import com.example.modo.domain.Member;
import com.example.modo.service.CommReplyService;

@RestController
public class CommReplyController {

	@Autowired
	private CommReplyService commReplyService;
	
	@GetMapping("/commReply/{id}/list")
	public ResponseEntity<List<CommReply>> getCommReply(@PathVariable Long id) {
		
		System.out.println(id);
		
		List<CommReply> commReply = commReplyService.getCommReplyById(id);
		
		return new ResponseEntity<>(commReply, HttpStatus.OK);
		
	}
	
	@PostMapping("/commReply/{id}")
	public ResponseEntity<?> insertCommReply(@PathVariable Long id, @RequestBody CommReply commReply) {
		
		System.out.println(commReply);
		System.out.println(id);
		
		commReplyService.insertCommReply(id, commReply);
		
		return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
		
	}
	
}
