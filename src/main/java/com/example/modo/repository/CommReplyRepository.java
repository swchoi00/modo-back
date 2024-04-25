package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.CommReply;
import com.example.modo.domain.Reply;

@Repository
public interface CommReplyRepository extends JpaRepository<CommReply, Long> {

	List<CommReply> findByCommPostno(Long postno);
	
}


