package com.example.modo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.MoimMember;
import com.example.modo.domain.MoimPhoto;


@Repository
public interface MoimPhotoRepository extends JpaRepository<MoimPhoto, Long> {
	 Optional<MoimPhoto> findById(Long id);
	 
	 @Query("SELECT m FROM MoimPhoto m WHERE m.moim.id = :moimId")
	 MoimPhoto findAllByMoimId(@Param("moimId") Long moimId);
}
