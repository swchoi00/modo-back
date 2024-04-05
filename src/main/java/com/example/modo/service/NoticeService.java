package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Notice;
import com.example.modo.repository.NoticeRepository;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;
	
	public void insertNotice(Notice notice) {
		
		noticeRepository.save(notice);
		
	}
	
	// 게시물 리스트 가져오기 (내림차순 정렬)
	public List<Notice> getNoticeList() {
		
		return noticeRepository.findAllByOrderByIdDesc();
	}
	
	// 게시물 가져오기
	public Notice getNotice(Long id) {
		
		return noticeRepository.findById(id).get();
	}
	
	// 게시물 수정
	public void updateNotice(Notice notice) {
		
		Notice originalNotice = noticeRepository.findById(notice.getId()).get();
		
		originalNotice.setTitle(notice.getTitle());
		originalNotice.setContent(notice.getContent());
		
		noticeRepository.save(originalNotice);
		
	}
	
	// 게시물 삭제
	public void deleteNotice(Long id) {
		
		noticeRepository.deleteById(id);
	}
	
}
