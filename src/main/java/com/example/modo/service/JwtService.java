package com.example.modo.service;

import java.security.Key;
import java.util.Date;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import javax.servlet.http.HttpServletRequest;

@Component
public class JwtService {
	
		// 토큰 만료 시간 (상수로 설정)
		static final long EXPIRATIONTIME = 24 * 60 * 60 * 1000;

		// jwt에서 헤더에 사용할 접두어
		static final String PREFIX = "Bearer";

		// 서명에 사용될 암호화시킨 Key
		static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

		// jwt 발급해주는 메소드
		public String getToken(String username) {
			String token = Jwts.builder().setSubject(username) // jwt 클레임 설정
					.setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME)) // 토큰 만료시간
					.signWith(key) // 서명
					.compact();

			return token;
		}

		// 토큰 추출해서 검사, 사용자 이름을 리턴
		public String getAuthUser(HttpServletRequest request) {

			String token = request.getHeader(HttpHeaders.AUTHORIZATION);

			// 토큰안에 들어있는 Username만 뽑아냄
			if (token != null) {
				String user = Jwts.parserBuilder() // 하나하나 분리시킴
						.setSigningKey(key).build().parseClaimsJws(token.replace(PREFIX, "")) // prefix 붙은거 지우기
						.getBody().getSubject();

				if (user != null)
					return user;
			}

			return null;
		}
}
