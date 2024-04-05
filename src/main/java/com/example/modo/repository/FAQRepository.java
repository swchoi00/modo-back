package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.FAQ;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {

	public List<FAQ> findAllByOrderByIdDesc();
	
}
