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

}
