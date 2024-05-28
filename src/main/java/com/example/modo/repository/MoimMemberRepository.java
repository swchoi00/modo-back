package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.MoimMember;

@Repository
public interface MoimMemberRepository  extends JpaRepository<MoimMember, Long>{

//	@Query("SELECT m FROM MoimMember m WHERE m.moim.id = :moimId")
	@Query("SELECT m FROM MoimMember m " +
	           "WHERE m.moim.id = :moimId " +
	           "ORDER BY CASE " +
	           "WHEN m.memberRole = 'leader' THEN 1 " +
	           "WHEN m.memberRole = 'manager' THEN 2 " +
	           "END, m.id ASC")
	List<MoimMember> findByMoimId(@Param("moimId") Long moimId);
	
	@Query("SELECT m FROM MoimMember m " +
		       "WHERE m.moim.id = :moimId AND m.memberRole = 'leader'")
		MoimMember findByLeader(@Param("moimId") Long moimId);
}
