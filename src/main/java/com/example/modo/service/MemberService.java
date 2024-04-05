package com.example.modo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.modo.domain.Member;
import com.example.modo.domain.OAuthType;
import com.example.modo.domain.RoleType;
import com.example.modo.repository.MemberRepository;
import com.google.gson.Gson;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Value("${kakao.default.password}")
	private String kakaoPassword;

	@Value("${google.default.password}")
	private String googlePassword;

	@Value("${naver.default.password}")
	private String naverPassword;

	public void insertMember(Member member) {

		member.setPassword(passwordEncoder.encode(member.getPassword()));
		member.setRole(RoleType.MEMBER);

		memberRepository.save(member);

	}

	public ResponseEntity<?> getResponseEntity(String username, String password) {

		UsernamePasswordAuthenticationToken upaToken = new UsernamePasswordAuthenticationToken(username, password);

		Authentication auth = authenticationManager.authenticate(upaToken);


		String jwt = jwtService.getToken(auth.getName());

		
		Member member = memberRepository.findByUsername(username).get();

		
		MultiValueMap<String, Member> body = new LinkedMultiValueMap<>();

		body.add("member", member);

		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
				.header(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "Authorization").body(body);
	}

	public Member checkMember(String username) {

		return memberRepository.findByUsername(username).orElseGet(() -> {
			return new Member();
		});

	}

	public String getKakaoAccessToken(String code) {

		HttpHeaders header = new HttpHeaders();

		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// body에다가 심을 것들 ( Kakao에서 시킴 )
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add("grant_type", "authorization_code");
		body.add("client_id", "116eb98e44e59c5f34ad8e04b02d0cd7"); // 각자 rest api key
		body.add("redirect_uri", "http://localhost:3000/oauth/kakao");
		body.add("code", code);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, header);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				request, String.class);

		String json = response.getBody();

		Gson gson = new Gson();
		Map<?, ?> data = gson.fromJson(json, Map.class);

		return (String) data.get("access_token");
	}

	public Member kakaoLogin(String accessToken) {

		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + accessToken);
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(header);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				request, String.class);

		String json = response.getBody();
		Gson gson = new Gson();
		Map<?, ?> data = gson.fromJson(json, Map.class);

		String username = (String) ((Map<?, ?>) data.get("properties")).get("nickname");
		String email = (String) ((Map<?, ?>) data.get("kakao_account")).get("email");

		Member member = new Member();

		member.setUsername("(k)" + email);
		member.setNickname(username);
		member.setRole(RoleType.MEMBER);
		member.setOauth(OAuthType.KAKAO);
		member.setPassword(kakaoPassword);

		return member;
	}

	public Member googleLogin(String token) {

		RestTemplate restTemplate = new RestTemplate();

		String userInfoEndPoint = "https://www.googleapis.com/oauth2/v1/userinfo";

		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + token);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(header);

		ResponseEntity<String> response = restTemplate.exchange(userInfoEndPoint, HttpMethod.GET, request,
				String.class);

		String userInfo = response.getBody();

		Gson gson = new Gson();

		Map<?, ?> data = gson.fromJson(userInfo, Map.class);

		Member member = new Member();

		member.setUsername("(g)" + (String) data.get("email"));
		member.setNickname((String) data.get("name"));
		member.setRole(RoleType.MEMBER);
		member.setOauth(OAuthType.GOOGLE);
		member.setPassword(googlePassword);

		return member;
	}

	public String getNaverAccessToken(String code, String state) {

		HttpHeaders header = new HttpHeaders();

		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// body에다가 심을 것들 ( Kakao에서 시킴 )
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();

		body.add("grant_type", "authorization_code");
		body.add("client_id", "xeAGTboYTyPVmgMgKHFC"); // 각자 rest api key
		body.add("client_secret", "uVdoX5S8vp");
		body.add("redirect_uri", "http://localhost:3000/oauth/naver");
		body.add("code", code);
		body.add("state", state);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, header);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange("https://nid.naver.com/oauth2.0/token", HttpMethod.POST,
				request, String.class);

		String json = response.getBody();

		Gson gson = new Gson();
		Map<?, ?> data = gson.fromJson(json, Map.class);

		return (String) data.get("access_token");
	}

	public Member naverLogin(String accessToken) {
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "Bearer " + accessToken);
		header.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(header);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> response = restTemplate.exchange("https://openapi.naver.com/v1/nid/me", HttpMethod.POST,
				request, String.class);

		String json = response.getBody();
		Gson gson = new Gson();
		Map<?, ?> data = gson.fromJson(json, Map.class);

		String username = (String) ((Map<?, ?>) data.get("response")).get("email");
		String nickname = (String) ((Map<?, ?>) data.get("response")).get("nickname");

		Member member = new Member();

		member.setUsername("(n)" + username);
		member.setNickname(nickname);
		member.setRole(RoleType.MEMBER);
		member.setOauth(OAuthType.NAVER);
		member.setPassword(naverPassword);

		return member;

	}

}
