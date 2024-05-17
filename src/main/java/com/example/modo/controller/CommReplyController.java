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
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.CommReply;
import com.example.modo.domain.Member;
import com.example.modo.service.CommReplyService;

@RestController
public class CommReplyController {

	@Autowired
	private CommReplyService commReplyService;
	
	// 댓글들 불러오기
	@GetMapping("/commReply/{id}/list")
	public ResponseEntity<List<CommReply>> getCommReply(@PathVariable Long id) {
				
		List<CommReply> commReply = commReplyService.getCommReplyById(id);
		
		return new ResponseEntity<>(commReply, HttpStatus.OK);
		
	}
	
	// 댓글 작성
	@PostMapping("/commReply/{id}")
	public ResponseEntity<?> insertCommReply(@PathVariable Long id, @RequestBody CommReply commReply) {
		
		System.out.println(commReply);
		System.out.println(id);
		
		commReplyService.insertCommReply(id, commReply);
		
		return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
		
	}
	
	 // 댓글 삭제
	 @DeleteMapping("/commReply/{rno}")
	    public ResponseEntity<?> deleteReply(@PathVariable Long rno) {
	        try {
	            commReplyService.deleteCommReply(rno);
	            return ResponseEntity.ok("댓글 삭제 완료");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("댓글 삭제 실패");
	        }
	    }
	 
	 // 댓글 수정
	 @PutMapping("/commReply_update/{rno}")
	 public ResponseEntity<?> updateReply(@PathVariable Long rno, @RequestBody CommReply commReply) {
		 
		 commReplyService.updateCommReply(rno, commReply);
		 
		 return new ResponseEntity<>("댓글 수정 완료", HttpStatus.OK);
		 
	 }
	 
	
	 @PostMapping("/like/{rno}")
	    public ResponseEntity<?> addLikeToCommReply(@PathVariable Long rno, @RequestBody Long userId) {
	        try {
	            commReplyService.addLikeToCommReply(rno, userId);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @PostMapping("/unlike/{rno}")
	    public ResponseEntity<?> removeLikeFromCommReply(@PathVariable Long rno, @RequestBody Long userId) {
	        try {
	            commReplyService.removeLikeFromCommReply(rno, userId);
	            return new ResponseEntity<>(HttpStatus.OK);
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	 
	
}
