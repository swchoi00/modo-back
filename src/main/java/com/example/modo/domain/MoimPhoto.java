package com.example.modo.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@SequenceGenerator(
		name =  "MoimPhoto_SEQ_GENERATOR",
		sequenceName = "MoimPhoto_SEQ",
		initialValue = 1, allocationSize = 1)
public class MoimPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MoimPhoto_GENERATOR")
    private Long moimPhotoNo;
    
    // 모임번호
    @JsonBackReference // 추가
    @OneToOne
    @JoinColumn(name = "moim_id")
    private Moim moim; 


    // 사진 저장 경로
    private String moimPhotoUrl;

    // 업로드 날짜
    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp moimPhotoDate;
    
    // 사진 타입(폴더)
    @Enumerated(EnumType.STRING)
    private PhotoType photoType;
    
    
    
    

}