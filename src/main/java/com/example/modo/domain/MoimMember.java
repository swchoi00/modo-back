package com.example.modo.domain;



import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @ManyToOne(fetch = FetchType.EAGER) // MoimMember 엔티티는 여러 개의 Moim 엔티티에 속할 수 있음
    @JoinColumn(name = "moim_id") // Moim 엔티티의 PK를 참조하는 외래 키
    @JsonBackReference
    private Moim moim; // Moim 엔티티 참조
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoimMember that = (MoimMember) o;
        return Objects.equals(id, that.id); // id가 멤버를 식별하는데 사용되는 경우
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // id가 멤버를 식별하는데 사용되는 경우
    }
    
    private String memberRole; // 모임멤버 권한 [leader, manager, member]

//    private Long memberNo; // 회원번호 (추후 참조할지 말지 조율)
    
    @ManyToOne(fetch = FetchType.EAGER) 
    @JoinColumn(name = "member_id")
    private Member member;
    
    @OneToMany(mappedBy = "moimMember", cascade = CascadeType.ALL, orphanRemoval = true) // CascadeType.REMOVE 추가
    @JsonIgnore
    private List<MoimComm> moimComms; // MoimComm와의 관계 추가
    
    @OneToMany(mappedBy = "moimMember", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonIgnore
    private List<MoimScheduleReply> moimScheduleReplies;
    
    @OneToMany(mappedBy = "moimMember", cascade = CascadeType.ALL, orphanRemoval = true) // MoimReply 관계 추가
    @JsonIgnore
    private List<MoimReply> moimReplies;

    @Column(updatable = false)
    @CreationTimestamp
    private Timestamp memberJoinDate; // 모임 가입날짜
	
    @Override
    public String toString() {
        return "MoimMember{" +
                "id=" + id +
                ", memberRole='" + memberRole + '\'' +
                ", memberJoinDate=" + memberJoinDate +
                '}';
    }
    
//    @JsonIgnore
//    @OneToMany(mappedBy = "members", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<MoimSchedule> moimSchedule; // MoimSchedule 참조
}
