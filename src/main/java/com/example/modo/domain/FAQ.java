package com.example.modo.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "FAQ")
@SequenceGenerator(
		name = "FAQ_SEQ_GENERATOR",
		sequenceName = "FAQ_SEQ",
		initialValue = 1, allocationSize = 1
		)
@NoArgsConstructor
@AllArgsConstructor
public class FAQ {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FAQ_SEQ_GENERATOR")
	private Long id;
	
	@Column(length = 100, nullable = false)
	private String title;
	
	@Lob
	@Column(nullable = false)
	private String content;
	
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp createDate;
	
	@Column(length = 100, nullable = false)
	private String adminName;
	
	
	
	
}
