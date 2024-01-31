package com.example.modo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.InquiryForm;
import com.example.modo.service.InquiryFormService;

@RestController
public class InquiryFormController {

	@Autowired
	private InquiryFormService inquiryFormService;
	
	
	@PostMapping("/inquiryForm_insert")
	public ResponseEntity<?> insertInquiryForm(@RequestBody InquiryForm inquiryForm) {
		
		System.out.println(inquiryForm);
		
		inquiryFormService.insertInquiryForm(inquiryForm);
		
		return new ResponseEntity<>("1:1문의 완료!", HttpStatus.OK);
		
	}
	
	
	
}
