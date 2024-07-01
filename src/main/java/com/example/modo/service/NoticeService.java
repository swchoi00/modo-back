package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.InquiryForm;
import com.example.modo.domain.Notice;
import com.example.modo.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	// 게시물 리스트 가져오기 (내림차순 정렬)
	public List<Notice> getNoticeList() {
		
		return noticeRepository.findAllByOrderByIdDesc();
	}
	
	// 게시물 가져오기
	public Notice getNotice(Long id) {
		
		return noticeRepository.findById(id).get();
	}
	
//	public void insertNotice(Notice notice) {
//		
//		noticeRepository.save(notice);
//		
//	}
//	
//	// 게시물 수정
//	public void updateNotice(Notice notice) {
//		
//		Notice originalNotice = noticeRepository.findById(notice.getId()).get();
//		
//		originalNotice.setTitle(notice.getTitle());
//		originalNotice.setContent(notice.getContent());
//		
//		noticeRepository.save(originalNotice);
//		
//	}
//	
//	// 게시물 삭제
//	public void deleteNotice(Long id) {
//		
//		noticeRepository.deleteById(id);
//	}
	
	// 공지사항 작성/수정
    public void noticeSave(Notice notice) {
    	noticeRepository.save(notice);
    	if(notice.getId() != null) {
        	Notice getNotice = noticeRepository.findById(notice.getId()).get();
        	getNotice.setTitle(notice.getTitle());
        	getNotice.setContent(notice.getContent());
        	
        	noticeRepository.save(getNotice);
    	}
    }
    
    public void deleteNotice(List<Long> list) {
        for (Long id : list) {
        	noticeRepository.deleteById(id);
        }
    }
	
	
}
