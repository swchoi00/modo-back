package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.CommReply;
import com.example.modo.domain.Reply;

@Repository
public interface CommReplyRepository extends JpaRepository<CommReply, Long> {

	List<CommReply> findByCommPostno(Long postno);
	
	// ANSI 조인 구문을 사용하여 댓글 리스트를 가져오는 쿼리
//    @Query("SELECT cr FROM CommReply cr JOIN cr.comm c WHERE c.postno = :postno")
//    List<CommReply> findCommRepliesByPostNoDesc(@Param("postno") Long postno);
	
	@Query("SELECT cr FROM CommReply cr JOIN cr.comm c WHERE c.postno = :postno ORDER BY cr.rno DESC")
	List<CommReply> findCommRepliesByPostNoDesc(@Param("postno") Long postno);

	
}


