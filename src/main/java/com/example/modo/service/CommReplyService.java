package com.example.modo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Comm;
import com.example.modo.domain.CommReply;
import com.example.modo.domain.Member;
import com.example.modo.repository.CommReplyRepository;
import com.example.modo.repository.CommunityRepository;
import com.example.modo.repository.MemberRepository;

@Service
public class CommReplyService {

	@Autowired
	private CommReplyRepository commReplyRepository;
	
	@Autowired
	private CommunityRepository communityRepository;
	
	@Autowired
	private MemberRepository memberRepository;
	
	public List<CommReply> getCommReplyById(Long rno) {
		
		return commReplyRepository.findByCommPostno(rno);
		
	}
	
	public void insertCommReply(Long postno, CommReply commReply) {
	    // 댓글에 대한 게시글을 가져옵니다.
	    Comm comm = communityRepository.findById(postno)
	            .orElseThrow(() -> new IllegalArgumentException("해당하는 게시글을 찾을 수 없습니다."));

	    // 댓글에 대한 작성자의 사용자 이름을 가져옵니다.
	    String username = commReply.getMember().getUsername();
	    System.out.println(username);

	    // 회원의 사용자 이름을 사용하여 회원 엔티티를 찾습니다.
	    Member member = memberRepository.findByUsername(username)
	            .orElseThrow(() -> new IllegalArgumentException("해당하는 회원을 찾을 수 없습니다."));
	    
	    System.out.println("멤버" + member);

	    // 댓글에 작성자를 설정합니다.
	    commReply.setMember(member);

	    // 댓글을 게시글에 연결하고 저장합니다.
	    commReply.setComm(comm);
	    commReplyRepository.save(commReply);
	}
	        
	    
	
}
