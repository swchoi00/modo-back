package com.example.modo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Comm;
import com.example.modo.service.CommunityService;
import com.example.modo.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

@RestController
public class CommunityController {

	@Autowired
	CommunityService communityService;
	
	@Autowired
	MemberService memberService;
	
	
	// --- ADMIN ---
	// 커뮤니티 삭제
    @DeleteMapping("/deleteCommunityList")
    public ResponseEntity<?> deleteCommList(@RequestBody List<Long> list) {
        try {
        	communityService.deleteCommunity(list);
            return ResponseEntity.ok("해당 커뮤니티 게시글 삭제 완료했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1:1문의 삭제 실패");
        }
    }
	

	@PostMapping("/upload")
	public ResponseEntity<String> uploadImage(@RequestPart("img") MultipartFile file) {
		
	    try {
	    	
	        String imageUrl = communityService.saveImage(file);
	        
	        return ResponseEntity.ok(imageUrl);
	        
	    } catch (IOException e) {
	    	
	        e.printStackTrace();
	        
	        return ResponseEntity.status(500).body("Image upload failed");
	    }
	}
	
	
	// 글 작성
	@PostMapping("/comm_insert")
	public ResponseEntity<String> insertPost(@RequestBody Map<String, Object> requestBody) {
	    try {
	        ObjectMapper objectMapper = new ObjectMapper();
	        String commInfoJson = (String) requestBody.get("commInfo");
	        Comm comm = objectMapper.readValue(commInfoJson, Comm.class);

	        communityService.insertComm(comm);

	        return new ResponseEntity<>("게시글 작성 완료!", HttpStatus.OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(500).body("Failed to insert post");
	    }
	}
	

	
	// 글 출력
	@GetMapping("/getCommList")
	public ResponseEntity<?> getCommList() {
		
		List<Comm> commList = communityService.getCommList();
		
		return new ResponseEntity<>(commList, HttpStatus.OK);
				
		
	}
	
	// 닉네임 불러오기
	@GetMapping("/comm_getNickname")
	public ResponseEntity<?> getNickName(@RequestBody String username) {
		
		String nickname = memberService.getNickname(username);
		
		return new ResponseEntity<>(nickname, HttpStatus.OK);
		
	}
	
	// 게시글 상세보기
	@GetMapping("/comm/{id}")
	public ResponseEntity<?> getComm(@PathVariable Long id) {
	      
	      Comm comm = communityService.getComm(id);
	      System.out.println(comm);
	      
	      return new ResponseEntity<>(comm, HttpStatus.OK);
	   }
	
	// 게시글 삭제
	  @DeleteMapping("/comm_delete/{id}")
	  public ResponseEntity<?> deleteComm(@PathVariable Long id, @RequestBody Map<String, List<String>> request) {
		  
	    List<String> images = request.get("images");
	    
	    communityService.deleteComm(id, images);
	    
	    return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
	  }
	
	// 게시글 수정
	@PutMapping("/comm_update/{id}")
	public ResponseEntity<?> updateComm(@PathVariable Long id, @RequestBody Comm comm ) {
		
		communityService.updateComm(id, comm);
		
		return new ResponseEntity<>("게시글 수정 완료", HttpStatus.OK);
	}
	
	
	
}
