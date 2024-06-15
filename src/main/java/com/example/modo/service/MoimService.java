package com.example.modo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.modo.domain.FAQ;
import com.example.modo.domain.Member;
import com.example.modo.domain.Moim;
import com.example.modo.domain.MoimComm;
import com.example.modo.domain.MoimMember;
import com.example.modo.domain.MoimPhoto;
import com.example.modo.domain.PhotoType;
import com.example.modo.repository.MemberRepository;
import com.example.modo.repository.MoimCommRepository;
import com.example.modo.repository.MoimMemberRepository;
import com.example.modo.repository.MoimPhotoRepository;
import com.example.modo.repository.MoimRepository;

@Service
public class MoimService {
	
	@Autowired
	MoimRepository moimRepository;
	
	@Autowired
	MoimPhotoRepository moimPhotoRepository;
	
	@Autowired
	MoimMemberRepository moimMemberRepository;
	
	@Autowired
	MemberRepository memberRepository;
	
	@Autowired
	MoimCommRepository moimCommRepository;
	

	// get 모임 목록 (모임멤버 몇명인지 리턴해야해서...이렇게 복잡하게 함)
	public List<Moim> getMoimList() {
	    List<Moim> moimList = moimRepository.findAll();
	    List<Moim> newMoimList = new ArrayList<Moim>(); 
	    
	    for (Moim moim : moimList) {
	        Long moimId = moim.getId();
	        moim.setMoimMemberNum(moimMemberRepository.findByMoimId(moimId).size());
	        newMoimList.add(moim);
	    }
	    return newMoimList;
	    //return moimRepository.findAll(); 기존코드
	}

	
	// 모임일정, 게시글, 모임인원, 갤러리 사진 등
	public Moim getMoimInfo(long id) {
		
		return moimRepository.findById(id).get();
	}
	
	
	// 모임이름 중복 확인 (추후 상단 코드와 같이 쓸 수 있지 않을까)
	public Moim getMoim(String moimname) {
		
		Moim moim = moimRepository.findByMoimname(moimname).orElseGet(() -> {
			return new Moim();
		});
		
		return moim;
	}
	
	// 모임 정보 저장 및 모임 정보 업데이트
	public Long insertMoim(Moim moim, String menu) {
		if(menu == "create") { //생성시에만 적용됨 (리더 객체 넣어야해서)
			Long leaderId = moim.getLeader().getId();
			Optional<Member> member = memberRepository.findById(leaderId);
			moim.setLeader(member.get());	
			moimRepository.save(moim);
			
			return moim.getId();
			
		} else {
		
		Moim savedMoim = moimRepository.findById(moim.getId()).get();
		
		savedMoim.setIntroduction(moim.getIntroduction());
		savedMoim.setTown(moim.getTown());
		savedMoim.setCity(moim.getCity());
		savedMoim.setHashtag(moim.getHashtag());
		
		moimRepository.save(savedMoim);
		return savedMoim.getId();		
		}
		
		
		
//		return savedMoim.getId();
	}
	
	// 모임 삭제
	public void deleteMoim(Long id) {
		
		moimRepository.deleteById(id);
		
	}
	
	

	// 🔥🔥모임멤버 리스트 가져오기
    public List<MoimMember> getMoimMemberList(Long id) {
		return moimMemberRepository.findByMoimId(id);
    }
	
	
	// 모임멤버 저장 (모임 생성 및 모임 가입 시 작동)
	public void updateMoimMember (Long userId, Long moimId, String role) {
		
	    Moim moim = moimRepository.findById(moimId).get();
	    Optional<Member> member = memberRepository.findById(userId);
	    
	    MoimMember moimMember = new MoimMember();
	    
	    moimMember.setMember(member.get()); // 유저 객체
	    moimMember.setMemberRole(role); // 권한
	    moimMember.setMoim(moim);// 모임 객체
	    moimMemberRepository.save(moimMember);
	}
	
	// 모임 가입 (멤버 추가_유저 id, 모임 id 받아옴)
	public List<MoimMember> joinMoim(Long userId, Long id) {
		
		Moim moim = moimRepository.findById(id)
			    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Moim not found with id " + id));
		
		if(moim.getBlockedMember().contains(userId)) {
			return Collections.emptyList(); // 강퇴당한 멤버면 빈 리스트 반환
		}
		
		updateMoimMember(userId, id, "member"); // 위에 있는 모임 멤버 저장 사용하고
		return moimMemberRepository.findByMoimId(id); // 오임 id 에 해당하는 모임멤버 리스트 리턴
	}
	
	
	// 서버 파일이 있는 곳에 moimPhoto 파일명에 저장됨
	private final String uploadDir = "moimPhoto";
	

    /**
     * 이미지 파일을 업로드하는 메서드
     * @param file 업로드할 이미지 파일
     * @return 저장된 파일의 경로
     * @throws IOException 파일 저장 중 발생한 예외
     */
    public String uploadImage(MultipartFile file, String photoType, long moimId) throws IOException {
    	
    	 PhotoType convertedPhotoType = PhotoType.valueOf(photoType.toUpperCase());
    	
//    	 String fileName = photoType + "1" + "_" + System.currentTimeMillis() + getFileExtension(file.getOriginalFilename());
//    	 String moimDir = uploadDir + File.separator + moimId;
//         String filePath = moimDir + File.separator + fileName;
    	 
//         Path path = Paths.get(filePath);
//         Files.createDirectories(path.getParent());
//         Files.write(path, file.getBytes());

    	 String fileName = photoType + "1" + "_" + System.currentTimeMillis() + getFileExtension(file.getOriginalFilename());
    	    String moimDir = "moimPhoto/" + moimId;

    	    // 백엔드 서버의 uploads 폴더에 저장하도록 경로 수정
    	    String filePath = "uploads/" + moimDir + "/" + fileName; // 경로 수정

    	    Path path = Paths.get(filePath);
    	    Files.createDirectories(path.getParent());
    	    Files.write(path, file.getBytes());
    	    

    	 

         Moim moim = moimRepository.findById(moimId).get();

         // 저장된 파일의 경로와 함께 현재 시간을 저장
         MoimPhoto moimPhoto = new MoimPhoto();
         moimPhoto.setMoimPhotoUrl(filePath);
         moimPhoto.setMoim(moim);
         moimPhoto.setPhotoType(convertedPhotoType);

         // MoimPhotoRepository를 사용하여 MoimPhoto를 저장
         moimPhotoRepository.save(moimPhoto);
         
         // 모임 엔티티에 모임 대표사진 번호 추가
//         moim.setMoimPhoto(moimPhoto);
         moimRepository.save(moim);

         return filePath; // 저장된 파일의 경로 반환
    }

    /**
     * 파일 이름에서 확장자를 추출하는 메서드
     * @param filename 파일 이름
     * @return 파일의 확장자
     */
    private String getFileExtension(String filename) {
        int lastIndex = filename.lastIndexOf(".");
        return filename.substring(lastIndex);
    }
    
    
    

 // 이미지 파일을 바이트 배열로 읽어오는 메서드 추가
    public byte[] readMoimThumbnailBytes(Long photoNo) throws IOException {
        // MoimPhotoRepository를 사용하여 해당 photoNo에 해당하는 이미지 정보를 가져옵니다.
        MoimPhoto moimPhoto = moimPhotoRepository.findById(photoNo).orElse(null);
        if (moimPhoto != null) {
            // 이미지 파일을 읽어와 바이트 배열로 변환하여 반환합니다.
            Path imagePath = Paths.get(moimPhoto.getMoimPhotoUrl());
            return Files.readAllBytes(imagePath);
        } else {
            throw new IOException("Moim photo not found");
        }
    }
    
    public void moimCommInsert(MoimComm moimComm) {
//    	System.out.println("****************************************");
//    	System.out.println(moimComm);
    	Long memberId = moimComm.getAuthorid();

    	Member member =  memberRepository.findById(memberId).get();
    	MoimMember moimMember = moimMemberRepository.findById(memberId).get();
    	moimComm.setMoimMember(moimMember);
    	System.out.println(moimComm);
    	moimCommRepository.save(moimComm);
    	
    }
    

    // 모임 게시글 리스트
    public List<MoimComm> getMoimCommList(Long moimId){
    	
    	Moim moim = moimRepository.findById(moimId).get();
    	
    	return moimCommRepository.findByMoim(moim);
    }
    
    // 모임 게시글 
	public MoimComm getMoimComm (Long moimCommId){
		MoimComm moimComm = moimCommRepository.findById(moimCommId).orElse(null);
//        if (moimComm != null) {
//            // Hibernate의 Lazy Loading 문제 해결을 위해 replies 필드 초기화
//            moimComm.
//        }
        return moimComm;
//    	return moimCommRepository.findById(moimCommId).get();
    }
    
    
    
    
    // 모임 탈퇴
    public void quitMoim(Long deleteMoimMemberId) {
    	moimMemberRepository.deleteById(deleteMoimMemberId);
    }
    
    
    //모임 멤버 role 설정 (매니저 지정/해제)
    public List<MoimMember> updateMoimMemberRole(Long moimMemberId) {
    	MoimMember updateMoimMember = moimMemberRepository.findById(moimMemberId).get();
    	String memberRole = updateMoimMember.getMemberRole();
    	if("member".equals(memberRole)) {
    		updateMoimMember.setMemberRole("manager");
    	}else if("manager".equals(memberRole)) {
    		updateMoimMember.setMemberRole("member");
    	}
    	moimMemberRepository.save(updateMoimMember);
    	
    	return moimMemberRepository.findByMoimId(updateMoimMember.getMoim().getId());
    }
}
