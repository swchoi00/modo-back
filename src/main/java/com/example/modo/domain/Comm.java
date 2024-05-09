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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "comm")
@SequenceGenerator(
		name = "COMM_SEQ_GENERATOR",
		sequenceName = "COMM_SEQ",
		initialValue = 1, allocationSize = 1)
@JsonIgnoreProperties({"replies"})
public class Comm {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMM_SEQ_GENERATOR")
	private Long postno; // 게시글 번호
	
	@Column(nullable = false, length = 100)
	private String categories; // 카테고리
	
	@Column(nullable = false, length = 100)
	private String postname; // 글  제목
	
	@Column(nullable = false, length = 100)
	private String author; // 작성자
	
	private Long views; // 조회수
	
	@Column(updatable = false)
	@CreationTimestamp
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	private Timestamp uploadDate; // 글 작성일
	
	@Lob
	@Column(nullable = false, length = 1500)
	private String content; // 글 내용
	
	@JsonManagedReference
	@OneToMany(mappedBy = "comm", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy("rno desc")
	private List<CommReply> replies;
	
	@Override
    public String toString() {
        return "Comm{" +
                "postno=" + postno +
                ", categories='" + categories + '\'' +
                ", postname='" + postname + '\'' +
                ", author='" + author + '\'' +
                ", views=" + views +
                ", uploadDate=" + uploadDate +
                ", content='" + content + '\'' +
                '}';
    }
	

}



