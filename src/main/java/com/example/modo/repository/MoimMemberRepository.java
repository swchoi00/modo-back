package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.MoimMember;

@Repository
public interface MoimMemberRepository  extends JpaRepository<MoimMember, Long>{

	@Query("SELECT m FROM MoimMember m WHERE m.moim.id = :moimId")
	List<MoimMember> findByMoimId(@Param("moimId") Long moimId);
}
