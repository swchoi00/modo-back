package com.example.modo.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
@Table(name = "MoimComm")
@SequenceGenerator(
		name = "MOIMCOMM_SEQ_GENERATOR",
		sequenceName = "MOIMCOMM_SEQ",
		initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({"replies"})
public class MoimComm {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOIMCOMM_SEQ_GENERATOR")
	private Long postno; // 게시글 번호
	
	@Column(nullable = false, length = 100)
	private String categories; // 카테고리
	
	@Column(nullable = false, length = 100)
	private String postname; // 글  제목
	
//	@Column(nullable = false, length = 100)
//	private String author; // 작성자
	
	@Column(length = 10000)
	private Long authorid;
	
	// 모임 엔티티 참조 추가
//	@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "moim_member_id") // 외래 키 컬럼 이름
    private MoimMember moimMember; // 모임 엔티티 참조
	
	private Long views; // 조회수
	
	@Column(updatable = false)
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp uploadDate; // 글 작성일
	
	@Lob
	@Column(nullable = false, length = 1500)
	private String content; // 글 내용
	
	// 모임 엔티티 참조 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "moim_id") // 외래 키 컬럼 이름
    private Moim moim; // 모임 엔티티 참조
	
}
