package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.FAQ;
import com.example.modo.domain.Notice;
import com.example.modo.repository.FAQRepository;

@Service
public class FAQService {

	@Autowired
	private FAQRepository faqRepository;
	
	public void insertFaq(FAQ faq) {
		
		faqRepository.save(faq);
	}
	
	public List<FAQ> getFaqList() {
		
		return faqRepository.findAllByOrderByIdDesc();
	}
	
	public FAQ getFaq(Long id) {
		
		return faqRepository.findById(id).get();
	}
	
//	public void updateFaq(FAQ faq) {
//		
//		FAQ originalFaq = faqRepository.findById(faq.getId()).get();
//		
//		originalFaq.setTitle(faq.getTitle());
//		originalFaq.setContent(faq.getContent());
//		
//		faqRepository.save(originalFaq);
//		
//	}
//	
//	public void deleteFaq(Long id) {
//		
//		faqRepository.deleteById(id);
//	}
	
	// 공지사항 작성/수정
    public void FAQSave(FAQ faq) {
    	faqRepository.save(faq);
    	if(faq.getId() != null) {
        	FAQ getFAQ = faqRepository.findById(faq.getId()).get();
        	getFAQ.setTitle(faq.getTitle());
        	getFAQ.setContent(faq.getContent());
        	
        	faqRepository.save(getFAQ);
    	}
    }
    
    public void deleteFAQ(List<Long> list) {
        for (Long id : list) {
        	faqRepository.deleteById(id);
        }
    }
	
}
