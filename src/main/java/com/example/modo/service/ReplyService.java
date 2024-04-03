package com.example.modo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.InquiryForm;
import com.example.modo.domain.Member;
import com.example.modo.domain.Reply;
import com.example.modo.repository.InquiryFormRepository;
import com.example.modo.repository.ReplyRepository;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;
	
	@Autowired
	private InquiryFormRepository inquiryFormRepository;
	
	public List<Reply> getReplyById(Long no) {
		
		return replyRepository.findByInquiryFormId(no);
	}

	public void insertReply(Long id, Reply reply, Member member) {
		
		InquiryForm inquiryForm = inquiryFormRepository.findById(id).get();
		
		reply.setInquiryForm(inquiryForm);
		
		replyRepository.save(reply);
		
	}
	
	public void deleteReply(Long no) {
		
		replyRepository.deleteById(no);
		
	}
	
	@Transactional
	public void updateReply(Reply reply) {
		
		Reply originalReply = (Reply)replyRepository.findById(reply.getId()).get();
		originalReply.setContent(reply.getContent());
		
	}
	
	
}
