package com.example.modo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.modo.domain.Member;
import com.example.modo.service.CommunityService;
import com.example.modo.service.MyPageService;

@RestController
public class MyPageController {
	
	@Autowired
	private MyPageService myPageService;
	

	@PostMapping("/updateInfo")
	public ResponseEntity<?> updateInfo (@RequestBody Member member) {
		
		myPageService.updateInfo(member);
		
		return new ResponseEntity<String>("수정이 완료되었습니다!", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/deleteAccount/{id}")
	public ResponseEntity<?> deleteAccount (@PathVariable Long id) {
		
		System.out.println(id);
		myPageService.deleteInfo(id);
		
		return new ResponseEntity<>("탈퇴가 완료되었습니다", HttpStatus.OK);
		
	}
	
	// ■■■■ 유저 프로필 사진 확인용 ■■■■
	@PostMapping("/userProfilePhoto/{id}")
	public ResponseEntity<?> userProfilePhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
	    System.out.println("■■■■■■■■■■■■■■■■■■" + id + "■■■■■■■■■■■■■■■■■■");
	    String filename = file.getOriginalFilename();
	    String contentType = file.getContentType();

	    System.out.println(filename);
	    System.out.println(contentType);

	    try {
	        myPageService.userProfilePhoto(id, file);
	        return new ResponseEntity<>(null, HttpStatus.OK);
	    } catch (IllegalStateException | IOException e) {
	        e.printStackTrace(); // 예외 처리
	        return new ResponseEntity<>("File upload failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	
	
	// 업로드한 이미지 가져오는 작업
	 @GetMapping("/userProfilePhoto/{id}")
	 public ResponseEntity<byte[]> userProfilePhoto(@PathVariable Long id) {
		 try {
	         // 서비스에서 이미지 파일을 바이트 배열로 읽어옵니다.
	         byte[] imageBytes = myPageService.userProfilePhoto(id);
	      // 이미지 파일의 확장자에 따라 Content-Type 설정
	         String contentType = myPageService.determineContentType(id);
	         return ResponseEntity.ok()
	                 .contentType(MediaType.parseMediaType(contentType))
	                 .body(imageBytes);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	     }
	 }



	
}
