package com.example.modo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.modo.repository.MemberRepository;

@Service
public class LoginService {

	@Autowired
	MemberRepository memberRepository;
	
	
	
}
