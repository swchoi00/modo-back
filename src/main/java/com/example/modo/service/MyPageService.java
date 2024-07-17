package com.example.modo.service;



import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.modo.domain.Member;
import com.example.modo.domain.MoimPhoto;
import com.example.modo.repository.MemberRepository;

import io.jsonwebtoken.io.IOException;

@Service
public class MyPageService {

	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public void updateInfo(Member member) {
        
        Member originalMember = memberRepository.findById(member.getId()).orElse(null);
        System.out.println("원래 정보 : " + originalMember);
        
        if(member.getPassword() == null || member.getPassword().isEmpty()) {
           originalMember.setUsername(member.getUsername());
           originalMember.setNickname(member.getNickname());
           originalMember.setProfileText(member.getProfileText());
        } else {
           originalMember.setUsername(member.getUsername());
           originalMember.setPassword(passwordEncoder.encode(member.getPassword()));         
        }
        
        memberRepository.save(originalMember);
        
        
     }
	
	@Transactional
	public void deleteInfo(Long id) {
		
		memberRepository.deleteById(id);
		
		
	}
	
	

	// ■■■■ 유저 프로필 사진 확인용 ■■■■
		 public void userProfilePhoto(Long id, MultipartFile file) throws IllegalStateException, java.io.IOException {
			 Member member = memberRepository.findById(id).get();
			 String absolutePath = new File("").getAbsolutePath() + File.separator;
			 
			// 파일 저장 위치
	        String path = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static"
	                + File.separator + "images" + File.separator + "userImg";
	        File userImg = new File(path); 
	
	        if (!userImg.exists()) {
	            // 폴더없으면 생성
	            userImg.mkdirs();
	        }
	
	        if (!file.isEmpty()) {
	            // 파일저장 이름
	            String originalFileName = file.getOriginalFilename();
	            // 확장자를 제외한 파일 이름과 확장자 추출
	            int lastIndex = originalFileName.lastIndexOf('.');
	            String fileName = originalFileName.substring(0, lastIndex);
	
	            String userImgName = fileName + System.nanoTime() + getFileExtension(file.getOriginalFilename());
	            
	            
	         // 파일 저장
	            userImg = new File(absolutePath + path + File.separator + userImgName);
	            System.out.println("파일 저장경로:" + absolutePath + path + File.separator + userImgName);
	            
	            try {
	            	deleteExistingProfilePhoto(member); // 기존 프로필 이미지 삭제 메서드 호출
	                file.transferTo(userImg);
	                // 새로운 Member 객체 생성 및 파일 경로 전달 (db 저장에 사용)
	                member.setMemberImage(path + File.separator + userImgName); // 실제 저장된 위치
	                 memberRepository.save(member); // 업데이트된 멤버 저장
	            } catch (IOException e) {
	                e.printStackTrace(); // 예외 처리
	            }
	        }
	        
	        
		 }
	
		 
	 private void deleteExistingProfilePhoto(Member member) {
		    if (member.getMemberImage() != null && !member.getMemberImage().isEmpty()) {
//		    	String absolutePath = new File("").getAbsolutePath() + File.separator;
//		        File existingFile = new File(absolutePath + member.getMemberImage());
		        File existingFile = new File(member.getMemberImage());
		        if (existingFile.exists()) {
		            if (existingFile.delete()) {
		                System.out.println("기존 프로필 사진 삭제 성공: " + existingFile.getAbsolutePath());
		            } else {
		                System.out.println("기존 프로필 사진 삭제 실패: " + existingFile.getAbsolutePath());
		            }
		        }
		    }
		}
	 
	 
	 //확장자 찾기
	 private String getFileExtension(String filename) throws IOException{
	        int lastIndex = filename.lastIndexOf(".");
	        return filename.substring(lastIndex);
    }
	 
	// 업로드한 이미지 가져오는 작업
    public byte[] userProfilePhoto(Long id) throws IOException, Exception {
    	Member member = memberRepository.findById(id).orElse(null);
        if (member.getMemberImage() != null) {
            // 이미지 파일을 읽어와 바이트 배열로 변환하여 반환합니다.
            Path imagePath = Paths.get(member.getMemberImage());
            return Files.readAllBytes(imagePath);
        } else {
            throw new IOException("Moim photo not found");
        }
    }
    
 // 이미지 파일의 확장자에 따라 Content-Type을 결정하는 메서드
    public String determineContentType(Long id) {
    	String filename= memberRepository.findById(id).get().getMemberImage();
    	int lastIndex = filename.lastIndexOf(".");
//        String fileType = filename.substring(lastIndex);
        String fileType = filename.substring(lastIndex + 1).toLowerCase();
	    
	    // 확장자에 따라 Content-Type 설정
	    switch (fileType) {
	        case "jpg":return "image/jpg";
	        case "jpeg":return "image/jpeg";
	        case "png": return "image/png";
	        case "gif": return "image/gif";
	        case "svg": return "image/svg+xml";
	        default: return "image/jpeg";
	    }
	    
    }
    
    
    
    
    
	 

}
