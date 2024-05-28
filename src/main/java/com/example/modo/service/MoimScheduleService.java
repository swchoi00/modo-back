package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimMember;
import com.example.modo.domain.MoimSchedule;
import com.example.modo.repository.MoimMemberRepository;
import com.example.modo.repository.MoimRepository;
import com.example.modo.repository.MoimScheduleRepository;

@Service
public class MoimScheduleService {

	@Autowired
	private MoimScheduleRepository moimScheduleRepository;
	
	@Autowired
	private MoimRepository moimRepository;
	
	@Autowired
	private MoimMemberRepository moimMemberRepository;
	
	public void insertMoimSchedule(Long id, MoimSchedule moimSchedule) {
		
		System.out.println(moimSchedule);
		
		Long memberId = 1L;
		
		Moim moim = moimRepository.findById(id).get();
		
		MoimMember moimMember = moimMemberRepository.findById(memberId).get();
		
		moimSchedule.setMoim(moim);
		moimSchedule.setMembers(moimMember);
		
		moimScheduleRepository.save(moimSchedule);
		
	}
	
	// 모임 아이디로 불러오기
	public List<MoimSchedule> getMoimSchedules(Long id) {
		
//		return moimScheduleRepository.findMoimScheduleByMoim_IdOrderByScheduleNoDesc(id);
		 return moimScheduleRepository.findMoimSchedulesByMoimId(id);
	}
	
	// 모임 스케쥴 상세 정보 (스케쥴 1개 정보)
	public MoimSchedule getMoimScheduleDetail(Long no) {
		return moimScheduleRepository.findById(no).get();
	}
	
}
