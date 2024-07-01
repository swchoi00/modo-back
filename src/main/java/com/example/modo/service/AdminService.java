package com.example.modo.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.domain.Member;
import com.example.modo.repository.MemberRepository;
import com.example.modo.repository.MoimRepository;


@Service
public class AdminService {
	
	@Autowired
	private MemberRepository memberRepository;
	
    @Autowired
    private MoimRepository moimRepository;

	// ---------- 회원관리
    
	// 회원 목록
	public List<Member> getMemberList() {
		return memberRepository.findAllByOrderByIdDesc();
	}
	
	// 회원 삭제
    public void deleteMember(List<Long> list) {
    	for (Long id : list) {
    		memberRepository.deleteById(id);
        }
    }
}
