package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.Notice;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long>  {

	public List<Notice> findAllByOrderByIdDesc();
	
}
