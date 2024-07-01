package com.example.modo.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "moimScheduleReply")
@SequenceGenerator(
		name = "SCHEDULEREPLY_SEQ_GENERATOR",
		sequenceName = "SCHEDULEREPLY_SEQ",
		initialValue = 1, allocationSize = 1)
public class MoimScheduleReply {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SCHEDULEREPLY_SEQ_GENERATOR")
	private Long rno;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "moim_member_id")
	private MoimMember moimMember;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "moim_schedule_id")
	private MoimSchedule moimSchedule;
	
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp CreateDate;
	
	
	
}
