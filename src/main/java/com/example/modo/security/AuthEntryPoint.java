package com.example.modo.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 인증되지 않은 사용자가 접근했을 때
		
		response.setContentType("application/json; charset=utf-8"); // 한글로 띄우기 위해 사용
		
		response.getWriter().write("인증 실패"); // 담을 메세지
		
		
	}
	
}
