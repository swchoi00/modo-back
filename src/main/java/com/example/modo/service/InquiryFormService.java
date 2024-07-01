package com.example.modo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.modo.domain.Comm;
import com.example.modo.domain.InquiryForm;
import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimComm;
import com.example.modo.domain.MoimMember;
import com.example.modo.repository.InquiryFormRepository;

@Service
public class InquiryFormService {

	@Autowired
	private InquiryFormRepository inquiryFormRepository;
	
	// --- USER ---
	// 1:1문의 글 추가
	public void insertInquiryForm(InquiryForm inquiryForm) {
		
		System.out.println("inquiryFormService : " + inquiryForm);
		
		inquiryFormRepository.save(inquiryForm);
	}
	
	// 1:1문의 글 수정
	public void updateInquiryForm(InquiryForm updatedInquiryForm) {
		
		
		InquiryForm inquiryForm = inquiryFormRepository.findById(updatedInquiryForm.getId()).get();
		
		inquiryForm.setTitle(updatedInquiryForm.getTitle());
		inquiryForm.setContent(updatedInquiryForm.getContent());
		
		inquiryFormRepository.save(inquiryForm);
		
	}
	
	// 1:1문의 글 삭제
	public void deleteInquiryForm(Long id) {
		
		inquiryFormRepository.deleteById(id);
		
	}
	
	public List<InquiryForm> userInquiryFormList(String username) {
		
		
		return inquiryFormRepository.findByWriterNameOrderByIdDesc(username);
	}
	
	public InquiryForm getInquiryForm(Long id) {
		
		return inquiryFormRepository.findById(id).get();
	}
	
	
	// --- ADMIN ---
	// 1:1문의 전체리스트 가져오기
	public List<InquiryForm> getInquiryList() {

		return inquiryFormRepository.findAllByOrderByIdDesc();
	}
	
	// 1:1문의 답변 작성/수정
    public void inquirySave(InquiryForm inquiry) {
    	Long inquiryId = inquiry.getId();
    	String inquiryAnswer = inquiry.getAnswer();
    	
    	InquiryForm getInquiry = inquiryFormRepository.findById(inquiryId).get();
    	getInquiry.setAnswer(inquiryAnswer);
    	inquiryFormRepository.save(getInquiry);
    	
    	System.out.println("inquiryFormService : " + getInquiry);
    	
    }
	
	// 1:1문의 삭제    
    public void deleteInquiry(List<Long> list) {
        for (Long id : list) {
        	inquiryFormRepository.deleteById(id);
        }
    }
	
}
