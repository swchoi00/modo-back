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

import com.example.modo.domain.Comm;
import com.example.modo.domain.InquiryForm;
import com.example.modo.domain.MoimComm;
import com.example.modo.service.InquiryFormService;

@RestController
public class InquiryFormController {

	@Autowired
	private InquiryFormService inquiryFormService;
	
	
	// --- USER ---
	@PostMapping("/inquiryForm_insert")
	public ResponseEntity<?> insertInquiryForm(@RequestBody InquiryForm inquiryForm) {
		
		inquiryFormService.insertInquiryForm(inquiryForm);
		
		return new ResponseEntity<>("1:1문의 완료!", HttpStatus.OK);
		
	}
	
	@GetMapping("/myInquiryForm/{username}")
	public List<InquiryForm> myInquiryForm(@PathVariable String username) {
		
		List<InquiryForm> myInquiryFormList = inquiryFormService.userInquiryFormList(username);
		
		return myInquiryFormList;
		
	}
	
	@GetMapping("/inquiryFormDetail/{id}")
	public ResponseEntity<?> getInquiryForm(@PathVariable Long id) {
		
		InquiryForm inquiryForm = inquiryFormService.getInquiryForm(id);
		
		return new ResponseEntity<>(inquiryForm, HttpStatus.OK);
		
	}
	
	// --- ADMIN ---
	// 1:1문의 전체 리스트 가져오기
	@GetMapping("/getInquiryList")
	public ResponseEntity<?> getInquiryList() {
		
		List<InquiryForm> inquiryList = inquiryFormService.getInquiryList();
		
		return new ResponseEntity<>(inquiryList, HttpStatus.OK);
		
	}
	
	
	// 1:1문의 답변 작성/수정
	@PostMapping("/inquirySubmit")
	public ResponseEntity<?> inquirySave(@RequestBody InquiryForm inquiry) {
	
		inquiryFormService.inquirySave(inquiry);
		
		return new ResponseEntity<> ("1:1문의 답변 완료했습니다.", HttpStatus.OK);
		
	}
	
	// 1:1문의 삭제
    @DeleteMapping("/deleteInquiryList")
    public ResponseEntity<?> deleteInquiryList(@RequestBody List<Long> list) {
        try {
        	inquiryFormService.deleteInquiry(list);
            return ResponseEntity.ok("해당 1:1문의글 삭제 완료했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1:1문의 삭제 실패");
        }
    }
	
}
