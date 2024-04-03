package com.example.modo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.MoimPhoto;


@Repository
public interface MoimPhotoRepository extends JpaRepository<MoimPhoto, Long> {
	 Optional<MoimPhoto> findById(Long id);
}
