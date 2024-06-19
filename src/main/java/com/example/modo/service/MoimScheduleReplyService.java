package com.example.modo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.MoimMember;
import com.example.modo.domain.MoimSchedule;
import com.example.modo.domain.MoimScheduleReply;
import com.example.modo.repository.MoimMemberRepository;
import com.example.modo.repository.MoimScheduleReplyRepository;
import com.example.modo.repository.MoimScheduleRepository;

@Service
public class MoimScheduleReplyService {

	@Autowired
	private MoimScheduleReplyRepository moimScheduleReplyRepository;
	
	@Autowired
	private MoimScheduleRepository moimScheduleRepository;
	
	@Autowired
	private MoimMemberRepository moimMemberRepository;
	
	public List<MoimScheduleReply> insertScheduleReply(Long no, MoimScheduleReply moimScheduleReply) {
        MoimSchedule moimSchedule = moimScheduleRepository.findById(no)
                .orElseThrow(() -> new NoSuchElementException("스케쥴이 존재하지 않습니다."));

        MoimMember moimMember = moimMemberRepository.findById(moimScheduleReply.getMoimMember().getId())
                .orElseThrow(() -> new NoSuchElementException("모임 멤버가 존재하지 않습니다."));

        moimScheduleReply.setMoimMember(moimMember);
        moimScheduleReply.setMoimSchedule(moimSchedule);
        moimScheduleReplyRepository.save(moimScheduleReply);

        // 해당 스케쥴에 대한 댓글 리스트 조회 및 반환
        List<MoimScheduleReply> updatedReplyList = moimScheduleReplyRepository.findByMoimSchedule(moimSchedule);
        
        return updatedReplyList;
    }
	
	public List<MoimScheduleReply> getMoimScheduleReplies(Long no) {
        MoimSchedule moimSchedule = moimScheduleRepository.findById(no)
                .orElseThrow(() -> new NoSuchElementException("스케쥴이 존재하지 않습니다."));

        // 해당 스케쥴에 대한 모든 댓글 리스트 조회
        List<MoimScheduleReply> replyList = moimScheduleReplyRepository.findByMoimSchedule(moimSchedule);
        
        return replyList;
    }
	
	public void deleteScheduleReply(Long rno) {
		
		moimScheduleReplyRepository.deleteById(rno);
		
	}
	
}
