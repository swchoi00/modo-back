package com.example.modo.controller;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.modo.domain.Moim;
import com.example.modo.domain.PhotoType;
import com.example.modo.service.MoimService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MoimPhotoController {

	@Autowired
	MoimService moimService; // 임시
	
//	 @PostMapping("/addMoimThumbnail")
//	 public ResponseEntity<String> addMoimThumbnail(@RequestParam("file") MultipartFile file,@RequestParam("file") MultipartFile file, @RequestParam("moimName") String moimName) {
//
//		 	try {
//		        moimService.uploadImage(file, moimName);
//		        return ResponseEntity.ok("이미지 업로드가 성공적으로 완료되었습니다.");
//		    } catch (IOException e) {
//		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//		                .body("이미지 업로드 중 오류가 발생했습니다.");
//		    }
//	}
	
	@PostMapping("/addMoimThumbnail")
    public ResponseEntity<String> addMoimThumbnail(@RequestParam("moimInfo") String moimInfo,	// 모임정보
                                                   @RequestParam("file") MultipartFile file,	// 모임 대표 파일
                                                   @RequestParam("photoType") String photoType){ 	// 모임이름 (열거형 ex) 대표, 일정, 갤러리 )
        
		
		ObjectMapper objectMapper = new ObjectMapper();
	    Moim moim;
	    try {
	        moim = objectMapper.readValue(moimInfo, Moim.class); // JSON을 Moim 객체로 변환
	        Long moimId = moimService.insertMoim(moim); // 모임을 DB에 저장하고 생성된 모임의 ID를 반환
	        moimService.uploadImage(file, photoType, moimId); // 생성된 모임의 ID를 사용하여 이미지 업로드
	        return ResponseEntity.ok("이미지 업로드가 성공적으로 완료되었습니다.");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("모임 정보를 읽어오는 중 오류가 발생했습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("이미지 업로드 중 오류가 발생했습니다.");
	    }
    }


	 
	 // 업로드한 이미지 가져오는 작업
	 @GetMapping(value = "/getMoimThumbnail/{photoNo}", produces = MediaType.IMAGE_JPEG_VALUE)
	 public ResponseEntity<byte[]> getMoimThumbnail(@PathVariable Long photoNo) {
	     try {
	         // 서비스에서 이미지 파일을 바이트 배열로 읽어옵니다.
	         byte[] imageBytes = moimService.readMoimThumbnailBytes(photoNo);
	         return ResponseEntity.ok()
	                 .contentType(MediaType.IMAGE_JPEG)
	                 .body(imageBytes);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	     }
	 }
	
}
