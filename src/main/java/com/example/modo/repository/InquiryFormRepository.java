package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.modo.domain.InquiryForm;

public interface InquiryFormRepository extends JpaRepository<InquiryForm, Long>{

	List<InquiryForm> findByWriterNameOrderByIdDesc(String username);
	
}
