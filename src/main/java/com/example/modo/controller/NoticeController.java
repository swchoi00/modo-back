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

import com.example.modo.domain.InquiryForm;
import com.example.modo.domain.Notice;
import com.example.modo.service.NoticeService;

@RestController
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	
	@GetMapping("/getNoticeList")
	public ResponseEntity<?> getNoticeList() {
		
		List<Notice> noticeList = noticeService.getNoticeList();
		
		return new ResponseEntity<>(noticeList, HttpStatus.OK);
	}
	
//	@PostMapping("/notice_insert")
//	public ResponseEntity<?> insertNotice(@RequestBody Notice notice ) {
//		
//		System.out.println(notice);
//		
//		noticeService.insertNotice(notice);
//		
//		return new ResponseEntity<>("게시글 작성완료", HttpStatus.OK);
//	}
//	
	@GetMapping("/noticeDetail/{id}")
	public ResponseEntity<?> getNotice(@PathVariable Long id) {
		
		Notice notice = noticeService.getNotice(id);
		
		return new ResponseEntity<>(notice, HttpStatus.OK);
		
	}
	
	// 공지사항 작성/수정
	@PostMapping("/noticeSubmit")
	public ResponseEntity<?> inquirySave(@RequestBody Notice notice) {
	
		noticeService.noticeSave(notice);
		
		return new ResponseEntity<> ("공지사항 작성 완료했습니다.", HttpStatus.OK);
		
	}
	
	// 공지사항 삭제
    @DeleteMapping("/deleteNoticeList")
    public ResponseEntity<?> deleteNoticeList(@RequestBody List<Long> list) {
        try {
        	noticeService.deleteNotice(list);
            return ResponseEntity.ok("해당 1:1문의글 삭제 완료했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1:1문의 삭제 실패");
        }
    }
}
