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
	
	@GetMapping("/myInquiryForm/{username}")
	public List<InquiryForm> myInquiryForm(@PathVariable String username) {
		
		System.out.println(username);
		
		List<InquiryForm> myInquiryFormList = inquiryFormService.userInquiryFormList(username);
		
		System.out.println(myInquiryFormList);
		
		return myInquiryFormList;
		
	}
	
	
}
