package com.example.modo.domain;

import javax.persistence.Entity;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

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

    private String moimPhotoUrl;

    private Date moimPhotoDate;

}