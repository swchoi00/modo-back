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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Notice")
@SequenceGenerator(
		name = "NOTICE_SEQ_GENERATOR",
		sequenceName = "NOTICE_SEQ",
		initialValue = 1, allocationSize = 1)
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOTICE_SEQ_GENERATOR")
	private Long id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Column(nullable = false, length = 100)
	private String content;
	
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createDate;
	
//	@ManyToOne(fetch = FetchType.EAGER) // 다대일) 1개의 공지사항이 한명의 회원과 연결
//	@JoinColumn(name = "username") 
//	private Member member;
	
	@Column(length = 100)
	private String adminName;
	
	
}
