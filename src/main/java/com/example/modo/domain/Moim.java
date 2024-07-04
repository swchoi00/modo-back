package com.example.modo.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "moim")
@SequenceGenerator(
    name = "MOIM_SEQ_GENERATOR",
    sequenceName = "MOIM_SEQ",
    initialValue = 1, 
    allocationSize = 1)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Moim {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MOIM_SEQ_GENERATOR")
    private Long id; // 모임번호
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member leader;

    @Column(length = 100)
    private String moimname; // 모임이름
    
    @Column(length = 100)
    private String category; // 카테고리 - 추후에 열거형으로 할 수도 있음
    
    @Column(length = 100)
    private String city; // 지역(시)
    
    @Column(length = 100)
    private String town; // 지역(구)
    
    @Column(length = 100)
    private String introduction; // 간단설명
    
    @Column(length = 1500)
    private String description; // 설명 : 나중에 추가하는 방식 nullable
    
    @ElementCollection
    @Column(name ="hashtag", length = 100)
    private List<String> hashtag; // 해시태그 : 나중에 추가하는 방식 nullable
    
    @ElementCollection
    @Column(name = "blockedMember", length = 100)
    private List<Long> blockedMember;
    
    @OneToMany(mappedBy = "moim", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<MoimMember> members = new ArrayList<>(); // MoimMember 엔티티와의 일대다 관계
    
    @OneToMany(mappedBy = "moim", orphanRemoval = true)
    private List<MoimSchedule> schedules; // MoimSchedule 엔티티와의 일대다 관계
    
    @OneToMany(mappedBy = "moim", fetch = FetchType.EAGER, orphanRemoval = true)
    @OrderBy("rno desc")
    @JsonIgnore
    private List<MoimReply> replies; // MoimReply 엔티티와의 일대다 관계
    
    @OneToMany(mappedBy = "moim", orphanRemoval = true)
    private List<MoimComm> moimComms; // MoimComm 엔티티와의 일대다 관계
    
    @OneToOne(mappedBy = "moim", cascade = CascadeType.REMOVE)
    private MoimPhoto moimPhoto; // MoimPhoto 엔티티와의 일대일 관계
    
    @Column(length = 100)
    private int moimMemberNum; // 모임 멤버 수
    
    @Override
    public String toString() {
        StringBuilder membersString = new StringBuilder();
        if (members != null) {
            for (MoimMember member : members) {
                membersString.append(member.getId()).append(", ");
            }
        }

        // 마지막 ", " 제거
        if (membersString.length() > 0) {
            membersString.setLength(membersString.length() - 2);
        }

        return "Moim{" +
                "id=" + id +
                ", moimname='" + moimname + '\'' +
                ", members=[" + membersString.toString() + "]" +
                '}';
    }
}
