package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimComm;

@Repository
public interface MoimCommRepository extends JpaRepository<MoimComm, Long>{

	List<MoimComm> findByMoim(Moim moim);
	
	List<MoimComm> findByAuthorid(Long autorid);
	
	@Query("SELECT mc FROM MoimComm mc JOIN mc.moimMember mm WHERE mm.member.id = :memberId")
	List<MoimComm> findByMemberId(@Param("memberId") Long memberId);

}
