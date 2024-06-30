package com.example.modo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.Moim;
import java.util.List;


@Repository
public interface MoimRepository extends JpaRepository<Moim, Long> {

	Optional<Moim> findByMoimname(String moimname);
//	Optional<Moim> findById(Long id);
	
	void deleteAllByIdIn(List<Long> ids);
}
