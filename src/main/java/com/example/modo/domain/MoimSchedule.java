package com.example.modo.domain;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "moimSchedule")
@SequenceGenerator(
		name = "SCHEDULE_SEQ_GENERATOR",
		sequenceName = "SCHEDULE_SEQ",
		initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class MoimSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULE_SEQ_GENERATOR")
	private Long scheduleNo;
	
	@ManyToOne
    @JoinColumn(name = "moim_id")
    private Moim moim; // Moim 엔티티와의 다대일 관계
	
	private String scheduleName; // 모임일정이름
	
	private String scheduleStartDate; // 시작날짜
		
	private String scheduleEndDate; // 종료날짜
	
	private String scheduleStartTime; // 시작시간
	
	private String scheduleEndTime; // 종료시간
	
	private String scheduleAddress; // 주소
	
	private String scheduleCost; // 비용
	
	private int scheduleMaxMember; // 모임인원
	
	private String scheduleDescription; // 모임설명
	
	@ElementCollection
	@Column(name = "joinedMember")
	private List<Member> joinedMember;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "moim_member_id")
	private MoimMember members; // MoimMember 참조
	
	private String moimSchedulePhotoUrl; // 모임일정사진
	
}
