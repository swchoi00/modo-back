package com.example.modo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.Moim;

@Repository
public interface MoimRepository extends JpaRepository<Moim, String> {

	Optional<Moim> findByMoimname(String moimname);
	
}
