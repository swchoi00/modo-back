package com.example.modo.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
	// JpaRepository<Member, Id>가 들어오는게 권장된다 (Member같은 경우엔 Long)
	
	Optional<Member> findByUsername(String username);

	Optional<Member> findById(Long id);
	
	//  회원의 닉네임을 반환하는 메소드
    default String findNickNameByUsername(String username) {
        // findByUsername 메소드를 사용하여 회원을 검색
        Optional<Member> memberOptional = findByUsername(username);
        
        // 회원이 존재하면 닉네임을 반환, 그렇지 않으면 null 반환
        return memberOptional.map(Member::getNickname).orElse(null);
    }

//	Optional<String> findNickNameByUsername(String username)
}
