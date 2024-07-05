package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.modo.domain.Comm;
import com.example.modo.domain.FAQ;

@Repository
@Transactional(readOnly = true)
public interface CommunityRepository extends JpaRepository<Comm, Long> {

	List<Comm> findAllByOrderByPostnoDesc();
	
	
}
