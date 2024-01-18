package com.example.modo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Moim;
import com.example.modo.repository.MoimRepository;

@Service
public class MoimService {
	
	@Autowired
	MoimRepository moimRepository;
	
	public Moim getMoim(String moimname) {
		
		Moim moim = moimRepository.findByMoimname(moimname).orElseGet(() -> {
			return new Moim();
		});
		
		return moim;
	}
	
	public void insertMoim(Moim moim) {
		
		moimRepository.save(moim);
	}
	

}
