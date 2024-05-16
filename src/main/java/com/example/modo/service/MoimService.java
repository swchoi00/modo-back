package com.example.modo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
	
	// get 모임 목록 
	public List<Moim> getMoimList() {
		
		  return moimRepository.findAll();
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
	public Long insertMoim(Moim moim) {
		
//		String nickname = memberRepository.findNickNameByUsername(moim.getLeadername());
//		Long leaderid = memberRepository.findIdByUsername(moim.getLeadername());
				
//		moim.setLeadername(nickname);
//		moim.setLeaderid(leaderid);
		
		Moim savedMoim = moimRepository.save(moim);
		return savedMoim.getId();
		
	}
	
	// 🔥🔥모임멤버 리스트 가져오기
	public List<MoimMember> getMemberList(Long id) {
		return moimMemberRepository.findByMoimId(id);
	}
	
	// 모임멤버 저장 (모임 생성시)
	public void updateMoimMember (Long userId, Long moimId, String role) {
		
	    Moim moim = moimRepository.findById(moimId).get();

	    MoimMember moimMember = new MoimMember();
	    
	    moimMember.setMemberNo(userId); // 유저 ID 값 설정
	    moimMember.setMemberRole(role); // 권한
	    moimMember.setMoim(moim);// 모임 객체
	    moimMemberRepository.save(moimMember);
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
    	
    	 String fileName = photoType + "1" + "_" + System.currentTimeMillis() + getFileExtension(file.getOriginalFilename());
    	 String moimDir = uploadDir + File.separator + moimId;
         String filePath = moimDir + File.separator + fileName;
         Path path = Paths.get(filePath);
         Files.createDirectories(path.getParent());
         Files.write(path, file.getBytes());

         // 저장된 파일의 경로와 함께 현재 시간을 저장
         MoimPhoto moimPhoto = new MoimPhoto();
         moimPhoto.setMoimPhotoUrl(filePath);
         moimPhoto.setMoimid(moimId);
         moimPhoto.setPhotoType(convertedPhotoType);
        
         // moimPhoto.setMoimPhotoDate(new Date()); // 현재 시간 설정

         // MoimPhotoRepository를 사용하여 MoimPhoto를 저장
         moimPhotoRepository.save(moimPhoto);

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
    	moimComm.setMember(member);
    	System.out.println(moimComm);
    	moimCommRepository.save(moimComm);
    }
    	
    public List<MoimMember> moimGet(Long id) {
    	
    	List<MoimMember> moimMember = moimMemberRepository.findByMoimId(id);
    	
    	return moimMember;
    }

    public List<MoimComm> getMoimCommList(Long moimId){
    	
    	Moim moim = moimRepository.findById(moimId).get();
    	
    	return moimCommRepository.findByMoim(moim);
    }
}
