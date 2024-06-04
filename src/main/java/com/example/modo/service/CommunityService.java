package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Comm;
import com.example.modo.repository.CommunityRepository;
import com.example.modo.repository.MemberRepository;


import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	private final String uploadDir = "uploads/";

    public String saveImage(MultipartFile file) throws IOException {
    	File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs(); // uploads 디렉터리 생성
        }

        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
        File targetFile = new File(uploadDir + fileName);
        file.transferTo(targetFile);
        return "/uploads/" + fileName;
    }
	
	// 커뮤니티 게시글 작성
	public void insertPost(Comm comm) {
		
		// 조회수
		comm.setViews((long)0);
		
		String nickname = memberRepository.findNickNameByUsername(comm.getAuthor());
		
		comm.setAuthor(nickname);
		
		
		communityRepository.save(comm);
	}
	
	public List<Comm> getCommList() {
		
		return communityRepository.findAllByOrderByPostnoDesc();
		
	}
	
	
	public Comm getPost(Long id) {
		
		Comm getComm = communityRepository.findById(id).get();
		
		// 조회수 중가
		getComm.setViews(getComm.getViews() + 1);
		
		communityRepository.save(getComm);
		
	      return getComm;
	   }
	
	
	// 게시글 삭제
	public void deleteComm(Long id) {
		
		communityRepository.deleteById(id);
		
	}
	
	// 게시글 수정
	public void updateComm(Long id, Comm comm) {
		
		Comm originalComm = communityRepository.findById(id).get();
		
		originalComm.setPostname(comm.getPostname()); // 글 제목
		originalComm.setContent(comm.getContent());
		
		communityRepository.save(originalComm);
		
		
		
	}
	
}
