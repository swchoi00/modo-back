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

import com.example.modo.domain.FAQ;
import com.example.modo.domain.Notice;
import com.example.modo.service.FAQService;

@RestController
public class FAQController {

	@Autowired
	private FAQService faqService;
	
	@GetMapping("/getFAQList")
	public ResponseEntity<?> getFaqList() {
		
		List<FAQ> faqList = faqService.getFaqList();
		
		return new ResponseEntity<>(faqList, HttpStatus.OK);
	}
	
//	@PostMapping("/faq_insert")
//	public ResponseEntity<?> insertFaq(@RequestBody FAQ faq) {
//		
//		faqService.insertFaq(faq);
//		
//		return new ResponseEntity<>("게시글 작성완료", HttpStatus.OK);
//	}
	
	@GetMapping("/faqDetails/{id}")
	public ResponseEntity<?> getFaq(@PathVariable Long id) {
		
		FAQ faq = faqService.getFaq(id);
		
		return new ResponseEntity<>(faq, HttpStatus.OK);
		
	}
	
	// FAQ 작성/수정
	@PostMapping("/FAQSubmit")
	public ResponseEntity<?> FAQSave(@RequestBody FAQ faq) {
	
		faqService.FAQSave(faq);
		
		return new ResponseEntity<> ("FAQ 작성 완료했습니다.", HttpStatus.OK);
		
	}
	
	// FAQ 삭제
    @DeleteMapping("/deleteFAQList")
    public ResponseEntity<?> deleteNoticeList(@RequestBody List<Long> list) {
        try {
        	faqService.deleteFAQ(list);
            return ResponseEntity.ok("해당 FAQ게시긓 삭제 완료했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1:1문의 삭제 실패");
        }
    }
	
}
