package com.example.modo.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.modo.service.JwtService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	   @Autowired
	   private JwtService jwtService;
	   
	   @Override
	   protected void doFilterInternal(HttpServletRequest  request, HttpServletResponse response, FilterChain filterChain)
	         throws ServletException, IOException {

	      String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
	      
	      if(jwt != null) {
	         String username = jwtService.getAuthUser(request); // 토큰안에 인증된 사용자의 userName
	         
	         Authentication authentication = 
	               new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
	         
	         SecurityContextHolder.getContext().setAuthentication(authentication);
	      }
	      
	      filterChain.doFilter(request, response);
	   }
}
