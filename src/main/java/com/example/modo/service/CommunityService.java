package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Comm;
import com.example.modo.repository.CommunityRepository;
import com.example.modo.repository.MemberRepository;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository communityRepository;

	@Autowired
	private MemberRepository memberRepository;


	// 서버 내 저장할 폴더 경로 설정
	private final String UPLOAD_DIR = "./uploads/";

	public String saveImage(MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			throw new IllegalStateException("업로드된 파일이 비어있습니다.");
		}

		// 파일 저장 경로 생성 (없을 경우)
//		Files.createDirectories(Paths.get(UPLOAD_DIR));
		File directory = new File(UPLOAD_DIR);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		// 파일명을 가져옴 (원본 파일명)
		String originalFileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
		// 서버에 저장할 파일 경로
		Path savePath = Paths.get(UPLOAD_DIR + originalFileName);

		// 파일 저장
//		Files.copy(file.getInputStream(), savePath);
		try (InputStream inputStream = file.getInputStream()) {
			Files.copy(inputStream, savePath);
		}

		// 절대 경로로 URL 생성
		String absoluteUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/uploads/")
				.path(originalFileName).toUriString();

		return absoluteUrl;
	}

//    // 이미지 파일 삭제
//    public void deleteImage(String imageUrl) {
//        try {
//            // 이미지 URL을 URI로 파싱하여 파일 이름 추출
//            URI uri = new URI(imageUrl);
//            String fileName = Paths.get(uri.getPath()).getFileName().toString();
//            
//            // 파일 경로 생성
//            Path imagePath = Paths.get(UPLOAD_DIR).resolve(fileName);
//            
//            // 이미지 파일 삭제
//            Files.deleteIfExists(imagePath);
//        } catch (URISyntaxException | IOException e) {
//            e.printStackTrace();
//        }
//    }

	// 커뮤니티 게시글 작성
	public void insertComm(Comm comm) {

		// 조회수
		comm.setViews((long) 0);

		String nickname = memberRepository.findNickNameByUsername(comm.getAuthor());

		comm.setAuthor(nickname);

		communityRepository.save(comm);
	}

	public List<Comm> getCommList() {

		return communityRepository.findAllByOrderByPostnoDesc();

	}

	public Comm getComm(Long id) {

		Comm getComm = communityRepository.findById(id).get();

		// 조회수 중가
		getComm.setViews(getComm.getViews() + 1);

		communityRepository.save(getComm);

		return getComm;
	}

	// 게시글 삭제
	public void deleteComm(Long id, List<String> images) {
	    Logger logger = LoggerFactory.getLogger(this.getClass());

	    // 게시물 삭제
	    communityRepository.deleteById(id);

	    // 이미지 파일 삭제
	    for (String imageUrl : images) {
	        try {
	            // URL 디코딩
	            String decodedImageUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.name());
	            String fileName = decodedImageUrl.substring(decodedImageUrl.lastIndexOf('/') + 1);
	            Path filePath = Paths.get(UPLOAD_DIR, fileName);
	            boolean deleted = Files.deleteIfExists(filePath);
	            if (deleted) {
	                logger.info("Deleted file: {}", filePath.toString());
	            } else {
	                logger.warn("File not found: {}", filePath.toString());
	            }
	        } catch (IOException e) {
	            logger.error("Failed to delete file", e);
	        }
	    }
	}
		

	// 게시글 수정
	public void updateComm(Long id, Comm comm) {

		Comm originalComm = communityRepository.findById(id).get();

		originalComm.setPostname(comm.getPostname()); // 글 제목
		originalComm.setContent(comm.getContent());

		communityRepository.save(originalComm);

	}


}
