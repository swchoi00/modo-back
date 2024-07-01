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
	public List<MoimReply> getMoimReplyById(Long moimCommId) {
		
		return moimReplyRepository.findMoimRepliesByMoimCommNoDesc(moimCommId);
	}
	
	public void insertMoimReply(Long moimid, MoimReply moimReply) {
		
		// 모임 찾기
	    Moim moim = moimRepository.findById(moimid).orElseThrow(() -> new IllegalArgumentException("Invalid moim ID"));

	    // 댓글 작성자 모임멤버 찾기
	    MoimMember moimMember = moimMemberRepository.findById(moimReply.getMoimMember().getId())
	            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 멤버 ID입니다"));

	    // 중복된 rno 값 확인
	    boolean isDuplicate = moim.getReplies().stream()
	            .anyMatch(reply -> reply.getRno().equals(moimReply.getRno()));
	    
	    if (isDuplicate) {
	        throw new IllegalArgumentException("중복된 rno 값입니다");
	    }

	    // 댓글 작성자 모임멤버 저장
	    moimReply.setMoimMember(moimMember);
	    moimReply.setMoim(moim);
	    
	    // 모임에 댓글 추가
	    moim.getReplies().add(moimReply);
	    
	    // 저장
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
