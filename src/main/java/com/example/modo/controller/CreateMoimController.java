package com.example.modo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimMember;
import com.example.modo.service.MemberService;
import com.example.modo.service.MoimService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CreateMoimController {
	
	@Autowired
	MoimService moimService;
	
	@Autowired
	MemberService memberService;
	
	
	
	//🔥🔥 모임멤버 리스트 리턴 (오류 파티...)
//	@GetMapping("/getMoimMemberList/{id}")
//	public ResponseEntity<?> getMoimMemberList(@PathVariable Long id) {
//		System.out.println(id);
//		List<MoimMember> moimMember = moimService.getMemberList(id);
//		return new ResponseEntity<>(moimMember, HttpStatus.OK);
//	}
	
	
	
	
	
	// ■■모임정보 업데이트■■ 
	@PostMapping("/updateMoimInfo")
	public ResponseEntity<?> updateMoimInfo(@RequestBody Moim moim){
		
		System.out.println(moim);
		moimService.insertMoim(moim);
		
		return new ResponseEntity<> ("수정완료!", HttpStatus.OK);
	}
	
	
	
	@PostMapping("/upDateLikedMoim")
	public ResponseEntity<?> upDateLikedMoim(@RequestBody Member member) {
	    
		Member updateMember = memberService.updateLikedMoims(member);
		
		return new ResponseEntity<>(updateMember,HttpStatus.OK);
	}
	
	
	
	@GetMapping("/moimList")
	public ResponseEntity<?> getMoimList() {
		
		List<Moim> moimList = moimService.getMoimList();
		
		return new ResponseEntity<>(moimList, HttpStatus.OK);
	}
	
	
	//추후 모임일정, 게시판, 모임 멤버 등도 받아와야함 
	@GetMapping("/moimInfo/{id}")
	public ResponseEntity<?> getMoimInfo(@PathVariable Long id) {
		Moim moimInfo = moimService.getMoimInfo(id);
		
		return new ResponseEntity<>(moimInfo, HttpStatus.OK);
	}
	
	
	@PostMapping("/moimnameCheck")
	public ResponseEntity<?> moimnameCheck(@RequestBody Moim moim) {
		
		System.out.println(moim);
		
		Moim moimnameCheck = moimService.getMoim(moim.getMoimname());
		
		if (moimnameCheck.getMoimname() != null && moimnameCheck.getMoimname().equals(moim.getMoimname())) {
			return new ResponseEntity<>("중복된 모임 이름입니다!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("사용 가능한 모임 이름입니다!", HttpStatus.OK);
		}
		
	}
	
	
//	@PostMapping("/createMoim")
//	public ResponseEntity<?> createMoim(@RequestBody Moim moim) {
//		
//		moimService.insertMoim(moim);
//		
//		return new ResponseEntity<>("모임 생성이 완료되었습니다!", HttpStatus.OK);
//		
//	}
	
	
	@PostMapping("/createMoim")
    public ResponseEntity<String> addMoimThumbnail(@RequestParam("moimInfo") String moimInfo,	// 모임정보
                                                   @RequestParam("file") MultipartFile file,	// 모임 대표 파일
                                                   @RequestParam("photoType") String photoType){ 	// 모임이름 (열거형 ex) 대표, 일정, 갤러리 )
        
		
		ObjectMapper objectMapper = new ObjectMapper();
	    Moim moim;
	    try {
	        moim = objectMapper.readValue(moimInfo, Moim.class); // JSON을 Moim 객체로 변환
//	        Long leaderId = moim.getLeaderid();
	        Long moimId = moimService.insertMoim(moim); // 모임을 DB에 저장하고 생성된 모임의 ID를 반환
	        moimService.uploadImage(file, photoType, moimId); // 생성된 모임의 ID를 사용하여 이미지 업로드
	        // moimService.updateMoimMember(leaderId, moimId, "leader");
	        return ResponseEntity.ok("모임 생성이 완료되었습니다!");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("모임 정보를 읽어오는 중 오류가 발생했습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("이미지 업로드 중 오류가 발생했습니다.");
	    }
    }
	
}
