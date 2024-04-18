package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Comm;
import com.example.modo.repository.CommunityRepository;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository communityRepository;
	
	// 커뮤니티 게시글 작성
	public void insertPost(Comm comm) {
		
		// 조회수
		comm.setViews((long)0);
		
		communityRepository.save(comm);
	}
	
	public List<Comm> getCommList() {
		
		return communityRepository.findAllByOrderByPostnoDesc();
		
	}
	
	
	public Comm getComm(Long id) {
	      return communityRepository.findById(id).get();
	   }
	
}
