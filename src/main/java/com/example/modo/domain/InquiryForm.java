package com.example.modo.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "inquiryForm")
@SequenceGenerator(
		name = "INQUIRYFORM_SEQ_GENERATOR",
		sequenceName = "INQUIRYFORM_SEQ",
		initialValue = 1, allocationSize = 1
		)
@NoArgsConstructor
@AllArgsConstructor
public class InquiryForm {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "INQUIRYFORM_SEQ_GENERATOR")
	private Long id;
	
	@Column(length = 100)
	private String title;
	
	@Lob // 큰 크기의 데이터를 저장하기 위한 Large Object 필드
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createDate;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "username")
//	private Member member;
	
	@Column(length = 100)
	private String writerName;
	
	@Column(columnDefinition = "number(1) default 0 not null")
	private boolean answerChk; // 답변 여부
	
	@JsonManagedReference
	@OneToMany(mappedBy = "inquiryForm", fetch = FetchType.EAGER)
	@OrderBy("no desc")
	private List<Reply> replyList;
	
}
