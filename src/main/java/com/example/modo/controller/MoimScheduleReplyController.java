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

import com.example.modo.domain.MoimScheduleReply;
import com.example.modo.service.MoimScheduleReplyService;

@RestController
public class MoimScheduleReplyController {

	@Autowired
	private MoimScheduleReplyService moimScheduleReplyService;
	
	@PostMapping("/moimScheduleReply/{no}")
    public ResponseEntity<List<MoimScheduleReply>> insertScheduleReply(@PathVariable Long no, @RequestBody MoimScheduleReply moimScheduleReply) {
        
		List<MoimScheduleReply> updatedReplyList = moimScheduleReplyService.insertScheduleReply(no, moimScheduleReply);
        
        return new ResponseEntity<>(updatedReplyList, HttpStatus.OK);
    }
	
	@GetMapping("/moimScheduleReply/{no}")
	 public ResponseEntity<List<MoimScheduleReply>> getMoimScheduleReplies(@PathVariable Long no) {
        
		List<MoimScheduleReply> replyList = moimScheduleReplyService.getMoimScheduleReplies(no);
        
        return new ResponseEntity<>(replyList, HttpStatus.OK);
    }
	
}
