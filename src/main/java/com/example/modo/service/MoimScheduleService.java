package com.example.modo.service;

import java.util.ArrayList;
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
		
		// 만약에 유저 ID가 아닌 모임 Id로 처리한다면?
		List<MoimMember> moimMemberList = new ArrayList<>();	// 모임멤버 리스트 객체 생성

		Moim moim = moimRepository.findById(id).get();

		MoimMember moimMember = moimMemberRepository.findByLeader(moim.getId());
		moimMemberList.add(moimMember); // 리더의 모임멤버 정보 추가
		
		moimSchedule.setMoim(moim);
		moimSchedule.setJoinedMember(moimMemberList); // 참여 멤버 리스트에 추가
//		moimSchedule.setMembers(moimMember); // ✅이건 정확히 뭔지 모르겠어유 설명 필요✅

		moimScheduleRepository.save(moimSchedule);
		
		
		
//System.out.println(moimSchedule);
//		
//		Long memberId = 1L;
//		
//		Moim moim = moimRepository.findById(id).get();
//		
//		MoimMember moimMember = moimMemberRepository.findById(memberId).get();
//		
//		moimSchedule.setMoim(moim);
//		moimSchedule.setMembers(moimMember);
//		
//		moimScheduleRepository.save(moimSchedule);
		
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
	
	public int moimScheduleJoin(Long id, MoimSchedule moimSchedule) {
		// 모임 멤버 번호, 모임스케쥴 객체 받음
		// A모임 멤버는 객체로 뽑아내고, 모임스케쥴 안에 있는 모임 멤버리스크 안에 이번에 받은 모임 멤버 있느지 확인
		// 있으면 모임 멤버 리스트에서 제거, 없으면 리스트에 추가 해서 update해야함
		MoimMember moimMember = moimMemberRepository.findById(id).get();
		List<MoimMember> moimMemberList = moimSchedule.getJoinedMember();
		
		moimMemberList.add(0, moimMember);
		
		moimSchedule.setJoinedMember(moimMemberList);
		moimScheduleRepository.save(moimSchedule);
		
		return 1;
	}
	
}
