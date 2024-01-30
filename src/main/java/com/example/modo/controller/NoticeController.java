package com.example.modo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Notice;
import com.example.modo.service.NoticeService;

@RestController
public class NoticeController {

	@Autowired
	private NoticeService noticeService;
	
	
	@GetMapping("/notice")
	public ResponseEntity<?> getNoticeList() {
		
		List<Notice> noticeList = noticeService.getNoticeList();
		
		return new ResponseEntity<>(noticeList, HttpStatus.OK);
	}
	
	
}