package com.example.modo.domain;

import java.sql.Timestamp;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
@Entity
@Table(name = "moimReply")
@SequenceGenerator(
    name = "MOIMREPLY_SEQ_GENERATOR",
    sequenceName = "MOIMREPLY_SEQ",
    initialValue = 1, 
    allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "rno")
public class MoimReply {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOIMREPLY_SEQ_GENERATOR")
    private Long rno;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Timestamp createDate;

    @ManyToOne
    @JoinColumn(name = "moim_id")
    @JsonBackReference
    private Moim moim;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "moim_member_id")
    private MoimMember moimMember;

    // [임시] 게시글 번호 
    @Column(length = 1000)
    private Long moimCommNo;
    
    @Override
    public String toString() {
        return "MoimReply{" +
               "rno=" + rno +
               ", content='" + content + '\'' +
               ", createDate=" + createDate +
               '}';
    }
}
