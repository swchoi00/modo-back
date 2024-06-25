package com.example.modo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Comm;
import com.example.modo.domain.FAQ;
import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimMember;
import com.example.modo.domain.Notice;
import com.example.modo.repository.CommunityRepository;
import com.example.modo.repository.FAQRepository;
import com.example.modo.repository.InquiryFormRepository;
import com.example.modo.repository.MemberRepository;
import com.example.modo.repository.MoimMemberRepository;
import com.example.modo.repository.MoimRepository;
import com.example.modo.repository.NoticeRepository;

@Service
public class AdminService {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	MoimRepository moimRepository;
	
	@Autowired
	MoimMemberRepository moimMemberRepository;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private FAQRepository faqRepository;
	
	@Autowired
	private InquiryFormRepository inquiryFormRepository;
	
	@Autowired
	private NoticeRepository noticeRepository;
	
	// ---------- 회원관리
	// 회원 목록
	public List<Member> getMemberList() {
		return memberRepository.findAllByOrderByIdDesc();
	}
	
	// 회원 삭제
	
	
	// ---------- 모임관리
	// 모임 목록
	public List<Moim> getMoimList() {
	    List<Moim> moimList = moimRepository.findAll();
	    List<Moim> newMoimList = new ArrayList<Moim>(); 
	    
	    for (Moim moim : moimList) {
	        Long moimId = moim.getId();
	        moim.setMoimMemberNum(moimMemberRepository.findByMoimId(moimId).size());
	        newMoimList.add(moim);
	    }
	    return newMoimList;
	}
	
	// 모임 삭제

	
	// ---------- 커뮤니티관리
	// 커뮤니티 목록
	public List<Comm> getCommList() {
		return communityRepository.findAllByOrderByPostnoDesc();
	}
	// 해당 커뮤니티 보기 
	// 커뮤니티 삭제
	
	
	// ---------- FAQ관리
	// FAQ 목록
	public List<FAQ> getFaqList() {
		
		return faqRepository.findAllByOrderByIdDesc();
	}
	// 해당 FAQ 보기 
	public FAQ getFaq(Long id) {
		
		return faqRepository.findById(id).get();
	}
	// FAQ 글작성/수정
	public void updateFaq(FAQ faq) {
		
		FAQ originalFaq = faqRepository.findById(faq.getId()).get();
		
		originalFaq.setTitle(faq.getTitle());
		originalFaq.setContent(faq.getContent());
		
		faqRepository.save(originalFaq);
		
	}
	// FAQ 삭제
	public void deleteFaq(Long id) {
		
		faqRepository.deleteById(id);
	}
	
	
	// ---------- 1:1문의관리
	// 1:1문의 목록
	// 해당 1:1문의 보기 
	// 1:1문의 답변작성/수정
	// 1:1문의 삭제
	
	// ---------- 공지사항관리
	// 공지사항 목록
	public List<Notice> getNoticeList() {
		
		return noticeRepository.findAllByOrderByIdDesc();
	}
	// 해당 공지사항 보기
	public Notice getNotice(Long id) {
		
		return noticeRepository.findById(id).get();
	}
	// 공지사항 글작성/수정

	// 공지사항 삭제
	public void deleteNotice(Long id) {
		
		noticeRepository.deleteById(id);
	}
}
