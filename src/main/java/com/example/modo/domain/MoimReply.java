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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "moimReply")
@SequenceGenerator(
		name = "MOIMREPLY_SEQ_GENERATOR",
		sequenceName = "MOIMREPLY_SEQ",
		initialValue = 1, allocationSize = 1)
public class MoimReply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOIMREPLY_SEQ_GENERATOR")
	private Long rno;
	
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createDate;
	
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "moim_id")
	private Moim moim;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;
	
	

}
