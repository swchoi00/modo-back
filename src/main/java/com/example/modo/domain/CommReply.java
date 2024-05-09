package com.example.modo.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "commReply")
@SequenceGenerator(
      name = "COMMREPLY_SEQ_GENERATOR",
      sequenceName = "COMMREPLY_SEQ",
      initialValue = 1, allocationSize = 1)
public class CommReply {


   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "COMMREPLY_SEQ_GENERATOR")
   private Long rno;
   
   @Column(nullable = false)
   private String content;
   
   @CreationTimestamp
   @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
   private Timestamp createDate;
   
   @JsonBackReference
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "postno")
   private Comm comm;
   
   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "member_id")
   private Member member;
   
   @ElementCollection
   @Column(name = "likeReply", length = 1000)
   private List<Long> likedReply;
 
   @Override
   public String toString() {
       return "CommReply{" +
               "rno=" + rno +
               ", content='" + content + '\'' +
               ", createDate=" + createDate +
               '}';
   }
   
}