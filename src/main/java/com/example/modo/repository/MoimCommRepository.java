package com.example.modo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimComm;

@Repository
public interface MoimCommRepository extends JpaRepository<MoimComm, Long>{

	List<MoimComm> findByMoim(Moim moim);
}
