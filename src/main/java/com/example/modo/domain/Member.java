package com.example.modo.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "member")
@SequenceGenerator(
		name = "MEMBER_SEQ_GENERATOR",
		sequenceName = "MEMBER_SEQ",
		initialValue = 1, allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor 
@Builder
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
	private Long id;
	
	@Column(length = 100) 
	private String username; // 아이디 (이메일)
	
	@Column(length = 100)
	private String password; // 비밀번호
	
	@Column(length = 100)
	private String nickname; // 모도에서 사용 할 닉네임
	
	// 핸드폰 번호 나중에 추가
	
	@Enumerated(EnumType.STRING)
	private RoleType role; // 권한 (MEMBER, ADMIN)
	
	@Enumerated(EnumType.STRING)
	private OAuthType oauth; // 소셜로그인 권한 (MODO, KAKAO, NAVER, GOOGLE)
	
	@Column(updatable = false)
	@CreationTimestamp
	private Timestamp createDate; // 계정 생성일

	
	
}
