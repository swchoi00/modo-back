package com.example.modo.controller;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimComm;
import com.example.modo.domain.MoimMember;
import com.example.modo.domain.MoimSchedule;
import com.example.modo.service.MemberService;
import com.example.modo.service.MoimService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class CreateMoimController {
	
	@Autowired
	MoimService moimService;
	
	@Autowired
	MemberService memberService;
	
	// --- ADMIN ---
	// 모임 삭제
    @DeleteMapping("/deleteMoimList")
    public ResponseEntity<?> deleteMoimList(@RequestBody List<Long> list) {
        try {
        	moimService.deleteMoim(list);
            return ResponseEntity.ok("해당 모임을 삭제 완료했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("1:1문의 삭제 실패");
        }
    }
	
	
	
	// 모임 가입
	@PostMapping("/joinMoim/{id}")
	public ResponseEntity<?> joinMoim (@RequestBody Long userId, @PathVariable Long id){
		
		List<MoimMember> moimMember = moimService.joinMoim(userId, id);
		
		if(moimMember.isEmpty()) {
			return new ResponseEntity<>("강퇴당한 멤버는 재가입 할 수 없습니다.", HttpStatus.OK);
		} else {
			return new ResponseEntity<> (moimMember , HttpStatus.OK);			
		}
		
	}
	
	
	
	// ■■모임정보 업데이트■■ 
	@PostMapping("/updateMoimInfo")
	public ResponseEntity<?> updateMoimInfo(@RequestBody Moim moim){
		System.out.println("■■■■■■■■■■■■■■■■■■■■■");
		System.out.println(moim.getDescription());
		moimService.insertMoim(moim, "update");
		
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
	    	
	    	System.out.println("Received moimInfo: " + moimInfo);
	        System.out.println("Received file: " + file.getOriginalFilename());
	        System.out.println("Received photoType: " + photoType);
	    	
	        moim = objectMapper.readValue(moimInfo, Moim.class); // JSON을 Moim 객체로 변환
	        Long leaderId = moim.getLeader().getId(); // 모임 리더 아이디
	        Long moimId = moimService.insertMoim(moim, "create"); // 모임을 DB에 저장하고 생성된 모임의 ID를 반환
	        moimService.uploadImage(file, photoType, moimId); // 생성된 모임의 ID를 사용하여 이미지 업로드
	        moimService.updateMoimMember(leaderId, moimId, "leader");
	        return ResponseEntity.ok("모임 생성이 완료되었습니다!");
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body("모임 정보를 읽어오는 중 오류가 발생했습니다.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                .body("이미지 업로드 중 오류가 발생했습니다.");
	    }
    }
	
	@DeleteMapping("/deleteMoim/{id}")
	public ResponseEntity<?> deleteMoim(@PathVariable Long id) {
		
		moimService.deleteMoim(id);
		
		return new ResponseEntity<>("모임 삭제가 완료되었습니다", HttpStatus.OK);
		
	}
	
	
	// 모임 게시글 작성 & 수정
	@PostMapping("/moimCommInsert")
	public ResponseEntity<?> moimCommInsert(@RequestBody MoimComm moimComm) {
	
		moimService.moimCommInsert(moimComm);
		
		return new ResponseEntity<> ("글쓰기 완료!", HttpStatus.OK);
		
	}
	
	// 모임 게시글 삭제
	@DeleteMapping("/deleteMoimComm/{no}")
	   public ResponseEntity<?> deleteMoimComm(@PathVariable Long no, @RequestBody Map<String, List<String>> request) {
	      
		List<String> images = request.get("images");
	      moimService.deleteMoimComm(no, images);
		    
	      
	      return new ResponseEntity<>("게시글 삭제 완료", HttpStatus.OK);
	      
	   }
	   
	 @GetMapping("/getUserIdMoimMemberList/{id}")
	   public ResponseEntity<?> getUserIdMoimMemberList(@PathVariable Long id) {
	      
	      List<Long> userMoimMemberNumList = moimService.getUserIdMoimMemberList(id);
	      
	      return new ResponseEntity<>( userMoimMemberNumList, HttpStatus.OK);
	      
	   }

	
	
	
	// 모임 게시글 리스트 가져오기
	@GetMapping("/getMoimCommList/{id}")
	public ResponseEntity<?> getMoimCommList(@PathVariable Long id){
	    List<MoimComm> moimCommList = moimService.getMoimCommList(id);

	    return new ResponseEntity<>(moimCommList, HttpStatus.OK);
	}
	
	//모임 게시글 가져오기
	@GetMapping("/getMoimCommDetail/{id}")
	public ResponseEntity<?> getMoimCommDetail(@PathVariable Long id){
	    MoimComm moimComm = moimService.getMoimComm(id);

	    return new ResponseEntity<>(moimComm, HttpStatus.OK);
	}
	
	@GetMapping("/myMoimCommList/{id}") // userid
	public ResponseEntity<?> myMoimCommList(@PathVariable Long id) {
		
		List<MoimComm> myMoimCommList = moimService.getMyMoimCommList(id);
		System.out.println(myMoimCommList);
		
		return new ResponseEntity<>(myMoimCommList, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/getMoimMemberList/{id}")
	public ResponseEntity<?> getMoimMemberList(@PathVariable Long id) {
		List<MoimMember> moimMember = moimService.getMoimMemberList(id);
		
		return new ResponseEntity<>(moimMember, HttpStatus.OK);
	}
	
	@DeleteMapping("/quitMoim/{deleteMoimMemberId}")
	public ResponseEntity<?> quitMoim (@PathVariable Long deleteMoimMemberId){
	    moimService.quitMoim(deleteMoimMemberId);
	    return new ResponseEntity<> ("모임탈퇴 완료!", HttpStatus.OK);
	}
	
	@PutMapping("/updateMoimMemberRole")
	public ResponseEntity<?> updateMoimMemberRole (@RequestBody Long moimMemberId){
		List<MoimMember> updateMoimMember =  moimService.updateMoimMemberRole(moimMemberId);
		return new ResponseEntity<> (updateMoimMember, HttpStatus.OK);
	}
	
	@PostMapping("/moimNoticeInsert/{id}")
	   public ResponseEntity<?> noticeInsert (@PathVariable Long id, @RequestBody List<Long> list) {
	      List<MoimComm> moimcommList = moimService.insertNoticeUpdate(id, list);
	      
	      return new ResponseEntity<>(moimcommList, HttpStatus.OK);
   }
	
}
