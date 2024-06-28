package com.example.modo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.modo.domain.Member;
import com.example.modo.service.MemberService;

@RestController
public class LoginController {
	
	
	@Autowired
	private MemberService memberService;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Member member) {
		
		String username = member.getUsername();
		String password = member.getPassword();
		
		return memberService.getResponseEntity(username, password);
		
	}
	
	@PostMapping("/passwordCheck")
	   public ResponseEntity<?> passwordCheck(@RequestBody Member member) {
	      
	      boolean isPasswordValid = memberService.passwordCheck(member);
	      
	      if (isPasswordValid) {
	           return new ResponseEntity<>(isPasswordValid, HttpStatus.OK);
	       } else {
	           return new ResponseEntity<>(isPasswordValid, HttpStatus.OK);
	       }
	      
	   }
	
	 private static final String ADMIN_USERNAME = "admin";
	 private static final String ADMIN_PASSWORD = "admin";

	    @PostMapping("/adminLogin")
	    public ResponseEntity<?> adminLogin(@RequestBody Member member) {
	        String username = member.getUsername();
	        String password = member.getPassword();

	        System.out.println("Received username: " + username);
	        System.out.println("Received password: " + password);

	        if (username == null || password == null) {
	            return ResponseEntity.badRequest().body("Username or password must not be null");
	        }

	        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
	            System.out.println("Login successful");
	            // 관리자 로그인 성공 시 처리할 로직을 여기에 추가할 수 있습니다.
	            return memberService.getAdminResponseEntity(username, password);
	        }

	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Only administrators can log in");
	    }
}
