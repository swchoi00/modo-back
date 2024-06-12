package com.example.modo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

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
	
	@Transactional
	public int moimScheduleJoin(Long id, MoimSchedule moimSchedule) {
	    // 해당 id의 모임 멤버를 찾음
	    MoimMember moimMember = moimMemberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("멤버를 찾을 수 없습니다."));

	    // 모임 스케줄에 참여한 멤버 리스트를 가져옴
	    List<MoimMember> moimMemberList = moimSchedule.getJoinedMember();
	    System.out.println(moimMemberList);

	    // null 체크
	    if (moimMemberList == null) {
	        moimMemberList = new ArrayList<>();
	    }

	    // 모임 멤버 리스트에 해당 moimMember가 있는지 확인
	    if (moimMemberList.contains(moimMember)) {
	        // 리스트에 있으면 제거
	        moimMemberList.remove(moimMember);
	        System.out.println(moimMemberList);
	    } else {
	        // 리스트에 없으면 추가
	        moimMemberList.add(moimMember);
	        System.out.println(moimMemberList);
	    }

	    // 업데이트된 멤버 리스트를 스케줄에 설정
	    moimSchedule.setJoinedMember(moimMemberList);

	    // 스케줄 저장
	    moimScheduleRepository.save(moimSchedule);

	    return 1; // 성공 시 1 반환
	}
	
	@Transactional
	public void deleteMoimSchedule(Long id) {
		
		moimScheduleRepository.deleteById(id);
		
	}
	
}
