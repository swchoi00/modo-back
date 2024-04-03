//package com.example.modo.domain;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToOne;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "liked_moim")
//@SequenceGenerator(
//		name = "LIKEDMOIM_SEQ_GENERATOR",
//		sequenceName = "LIKEDMOIM_SEQ",
//		initialValue = 1, allocationSize = 1
//		)
//public class LikedMoim {
//
//	@Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LIKEDMOIM_SEQ_GENERATOR")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member member;
//
//    @Column(name = "moim_no")
//    private Long moimNo;
//	
//}
