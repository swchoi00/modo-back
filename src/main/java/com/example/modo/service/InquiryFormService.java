package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.InquiryForm;
import com.example.modo.repository.InquiryFormRepository;

@Service
public class InquiryFormService {

	@Autowired
	private InquiryFormRepository inquiryFormRepository;
	
	// 1:1문의 글 추가
	public void insertInquiryForm(InquiryForm inquiryForm) {
		
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
	
	
	
	
}
