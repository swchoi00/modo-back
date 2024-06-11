package com.example.modo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimMember;
import com.example.modo.domain.MoimReply;
import com.example.modo.repository.MoimMemberRepository;
import com.example.modo.repository.MoimReplyRepository;
import com.example.modo.repository.MoimRepository;

@Service
public class MoimReplyService {

	@Autowired
	private MoimReplyRepository moimReplyRepository;
	
	@Autowired
	private MoimRepository moimRepository;
	
	@Autowired
	private MoimMemberRepository moimMemberRepository;
	
	// 모임에 해당하는 댓글들 불러오는 서비스 코드
	public List<MoimReply> getMoimReplyById(Long moimid) {
		
		return moimReplyRepository.findMoimRepliesByMoimIdDesc(moimid);
	}
	
	public void insertMoimReply(Long moimid, MoimReply moimReply) {
		System.out.println(moimReply);
		// 모임 찾기
		Moim moim = moimRepository.findById(moimid).get();
		
		String username = moimReply.getMoimMember().getMember().getUsername();
		Long userid = moimReply.getMoimMember().getMember().getId();
		
		System.out.println("모임댓글 작성자 : " + username);
		
		MoimMember moimMember = moimMemberRepository.findById(userid).get();
		
//		 댓글 작성자 모임멤버 저장
		moimReply.setMoimMember(moimMember);
		moimReply.setMoim(moim);
		moimReplyRepository.save(moimReply);
		
	}
	
	@Transactional
	public void deleteMoimReply(Long rno) {
		
		moimReplyRepository.deleteById(rno);
	}
	
	public void updateMoimReply(Long rno, MoimReply moimReply) {
		
		MoimReply oriMoimReply = moimReplyRepository.findById(rno).get();
		
		oriMoimReply.setContent(moimReply.getContent());
		
		moimReplyRepository.save(oriMoimReply);
		
	}
}
