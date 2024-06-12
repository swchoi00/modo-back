package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.MoimReply;

@Repository
public interface MoimReplyRepository extends JpaRepository<MoimReply, Long> {
	
	List<MoimReply> findByMoim_id(Long id); // 모임에 해당되는 댓글들 불러오기
	
	// moim_id로 MoimReply를 찾아서 rno 내림차순으로 정렬하는 메서드
    @Query("SELECT mr FROM MoimReply mr JOIN mr.moim m WHERE m.id = :moimId ORDER BY mr.rno DESC")
    List<MoimReply> findMoimRepliesByMoimIdDesc(@Param("moimId") Long moimId);

}
