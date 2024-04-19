package com.example.modo.domain;



import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "moimMember")
@SequenceGenerator(
		name = "MOIMMEMBER_SEQ_GENERATOR",
		sequenceName = "MOIMMEMBER_SEQ",
		initialValue = 1, allocationSize = 1
		)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoimMember {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOIMMEMBER_SEQ_GENERATOR")
    private Long id;

    @ManyToOne // MoimMember 엔티티는 여러 개의 Moim 엔티티에 속할 수 있음
    @JoinColumn(name = "moim_id") // Moim 엔티티의 PK를 참조하는 외래 키
    private Moim moim; // Moim 엔티티 참조

    private String memberRole; // 모임멤버 권한

    private Long memberNo; // 회원번호 (추후 참조할지 말지 조율)

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp memberJoinDate; // 모임 가입날짜
	
}
