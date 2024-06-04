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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
//	private final String uploadDir = "uploads";
//
//    public String saveImage(MultipartFile file) throws IOException {
//
//    	File dir = new File(uploadDir);
//        if (!dir.exists()) {
//            dir.mkdirs(); // uploads 디렉터리 생성
//        }
//
//        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
//        File targetFile = new File(uploadDir + fileName);
//        file.transferTo(targetFile);
//        return "uploads/" + fileName;
//
//    }
	
 // 서버 내 저장할 폴더 경로 설정
    private final String UPLOAD_DIR = "./uploads/";

    public String saveImage(MultipartFile file) throws IOException {
        // 파일이 비어있는지 확인
        if (file.isEmpty()) {
            throw new IllegalStateException("업로드된 파일이 비어있습니다.");
        }

        // 파일 저장 경로 생성 (없을 경우)
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // 파일명을 가져옴 (원본 파일명)
        String originalFileName = System.currentTimeMillis() + "-" +file.getOriginalFilename();
        // 서버에 저장할 파일 경로
        Path savePath = Paths.get(UPLOAD_DIR + originalFileName);

        // 파일 저장
        Files.copy(file.getInputStream(), savePath);

        // 저장된 파일의 경로나 파일명을 반환하거나, 저장 성공 메시지 반환
        return "./uploads/" + originalFileName;
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
