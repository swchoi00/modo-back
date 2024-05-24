package com.example.modo.domain;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member leader;

//	@Column(length = 100)
//	private Long leaderid; // 모임장 아이디
	
//	@Column(length = 100)
//	private String leadername; // 모임장 이름
	
	@Column(length = 100)
	private String moimname; // 모임이름
	
	@Column(length = 100)
	private String category; // 카테고리 - 추후에 열거형으로 할 수도 있음
	
	@Column(length = 100)
	private String city; // 지역(시)
	
	@Column(length = 100)
	private String town; // 지역(구)
	
	@Column(length = 100)
	private String introduction; // 간단설명
	
	@Column(length = 1500)
	private String description; // 설명 : 나중에 추가하는 방식 nullable
	
	@ElementCollection
	@Column(name ="hashtag", length = 100)
	private List<String> hashtag; // 해시태그 : 나중에 추가하는 방식 nullable
	
	@ElementCollection
	@Column(name = "blockedMember", length = 100)
	private List<Long> blockedMember;
	
	@JsonIgnore
	@OneToMany(mappedBy = "moim", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoimMember> members; // MoimMember 엔티티와의 일대다 관계
	
	@JsonIgnore
	@OneToMany(mappedBy = "moim", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MoimSchedule> schedules; // MoimSchedule 엔티티와의 일대다 관계
	
	@JsonManagedReference
	@OneToMany(mappedBy = "moim", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("rno desc")
	private List<MoimReply> replies;
	
	@Column(length = 100)
	private int moimMemberNum; // 모임이름
}
