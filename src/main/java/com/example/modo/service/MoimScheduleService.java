package com.example.modo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

	public void insertMoimSchedule(Long moimId, MoimSchedule moimSchedule) {
		// 모임 멤버 리스트 객체 생성
		List<Long> joinedMemberList = new ArrayList<>();

		// 모임 객체 조회
		Moim moim = moimRepository.findById(moimId).orElseThrow(() -> new NoSuchElementException("모임을 찾을 수 없습니다."));

		// 모임의 리더를 찾아서 joinedMemberList에 추가
		MoimMember moimLeader = moimMemberRepository.findByLeader(moimId);
		if (moimLeader != null) {
			joinedMemberList.add(moimLeader.getId());
		}

		// 모임 일정에 모임 객체와 joinedMemberList를 설정
		moimSchedule.setMoim(moim);
		moimSchedule.setJoinedMember(joinedMemberList);

		// 모임 일정 저장
		moimScheduleRepository.save(moimSchedule);
	}

	// 모임 아이디로 불러오기
	public List<MoimSchedule> getMoimSchedules(Long id) {

//		return moimScheduleRepository.findMoimScheduleByMoim_IdOrderByScheduleNoDesc(id);
		return moimScheduleRepository.findMoimSchedulesByMoimId(id);
	}

	// 모임 스케쥴 상세 정보 (스케쥴 1개 정보)
	public MoimSchedule getMoimScheduleDetail(Long no) {
		MoimSchedule moimScheduleInfo = moimScheduleRepository.findById(no).get();
		return moimScheduleInfo;
	}

	// 모임 스케쥴 멤버 가져오기
	public List<MoimMember> getMoimSheduleMemberList(Long no) {
		MoimSchedule moimScheduleInfo = moimScheduleRepository.findById(no).get();
		List<Long> joinMemberNo = moimScheduleInfo.getJoinedMember();
		List<MoimMember> joinMemberList = new ArrayList<>();
		for (Long memberId : joinMemberNo) {
			// memberId를 사용하여 MoimMember 객체를 생성 (또는 가져오기)
			MoimMember member = moimMemberRepository.findById(memberId).get();
			joinMemberList.add(member);
		}
		return joinMemberList;
	}

	@Transactional
	public int moimScheduleJoin(Long moimMemberId, Long moimScheduleId) {
		// 해당 id의 모임 멤버를 찾음
		MoimMember moimMember = moimMemberRepository.findById(moimMemberId)
				.orElseThrow(() -> new NoSuchElementException("멤버를 찾을 수 없습니다."));

		// 모임 스케줄을 찾음
		MoimSchedule moimSchedule = moimScheduleRepository.findById(moimScheduleId)
				.orElseThrow(() -> new NoSuchElementException("모임 일정을 찾을 수 없습니다."));

		// 모임 스케줄에 참여한 멤버 리스트를 가져옴
		List<Long> joinedMemberList = moimSchedule.getJoinedMember();
		if (joinedMemberList == null) {
			joinedMemberList = new ArrayList<>();
		}

		// 모임 멤버 리스트에 해당 moimMemberId가 있는지 확인
		if (joinedMemberList.contains(moimMemberId)) {
			// 리스트에 있으면 제거
			joinedMemberList.remove(moimMemberId);
		} else {
			// 리스트에 없으면 추가
			joinedMemberList.add(moimMemberId);
		}

		// 업데이트된 멤버 리스트를 스케줄에 설정
		moimSchedule.setJoinedMember(joinedMemberList);

		// 스케줄 저장
		moimScheduleRepository.save(moimSchedule);

		return 1; // 성공 시 1 반환
	}

	@Transactional
	public void deleteMoimSchedule(Long id) {

		moimScheduleRepository.deleteById(id);

	}

	public List<Long> findMoimMemberIdsByUserId(Long userId) {
		List<MoimMember> moimMembers = moimMemberRepository.findByMemberId(userId);

		// MoimMember 엔티티에서 moimMemberId 리스트를 추출
		List<Long> moimMemberIds = moimMembers.stream().map(MoimMember::getId) // getId()는 MoimMember 엔티티에서 ID를 추출하는
																				// 메서드입니다. 실제로 존재하는 메서드를 사용해야 합니다.
				.collect(Collectors.toList());

		return moimMemberIds;
	}


	public List<Long> getJoinedMembersByUserId(Long userId) {
		List<Long> joinedMembers = new ArrayList<>();

	    // 특정 사용자가 속한 모임 멤버들 조회
	    List<MoimMember> moimMembers = moimMemberRepository.findByMemberId(userId);
	    List<Long> moimMemberIds = moimMembers.stream()
	                                          .map(MoimMember::getId)
	                                          .collect(Collectors.toList());

	    // 사용자가 속한 모임 멤버들이 참여한 스케줄 조회
	    List<MoimSchedule> schedules = moimScheduleRepository.findByJoinedMemberInQuery(moimMemberIds);
	    System.out.println(schedules);
	    
	    // 참여한 스케줄의 ID 추가
	    for (MoimSchedule schedule : schedules) {
	        List<Long> joinedMemberIds = schedule.getJoinedMember();
	        if (joinedMemberIds != null && !joinedMemberIds.isEmpty() && moimMemberIds.stream().anyMatch(joinedMemberIds::contains)) {
	            joinedMembers.add(schedule.getScheduleNo());
	        }
	    }

	    return joinedMembers;
    }
	
	public List<MoimSchedule> findAllJoinedMembers() {
        return moimScheduleRepository.findAll();
    }
	
}
