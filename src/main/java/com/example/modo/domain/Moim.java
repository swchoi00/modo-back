package com.example.modo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "moim")
@SequenceGenerator(
		name =  "MOIM_SEQ_GENERATOR",
		sequenceName = "MOIM_SEQ",
		initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moim {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOIM_SEQ_GENERATOR")
	private Long id; // 모임번호
	
	@Column(length = 100)
	private String leadername; // 모임장
	
	@Column(length = 100)
	private String moimname; // 모임이름
	
	@Column(length = 100)
	private String category; // 카테고리 - 추후에 열거형으로 할 수도 있음
	
	@Column(length = 100)
	private String location; // 지역 - 추후에 열거형으로 할 수도 있음
	
	@Column(length = 100)
	private String introduction; // 간단설명
	
	@Column(length = 100)
	private String description; // 설명 : 나중에 추가하는 방식 nullable
	
	@Column(length = 100)
	private String hashtag; // 해시태그 : 나중에 추가하는 방식 nullable
	
	
	
}
