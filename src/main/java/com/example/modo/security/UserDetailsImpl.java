package com.example.modo.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.modo.domain.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Member member;
	
	private Map<String, Object> attributes;
	
	public UserDetailsImpl(Member member) {
		this.member = member;
	}
	
	public UserDetailsImpl(Member member, Map<String, Object> attributes) {
		this.member = member;
		this.attributes = attributes;
	}
	
	
	// 인증된 사용자에 대한 권한목록을 가져옴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> roleList = new ArrayList<>();
		
		roleList.add(new GrantedAuthority() {
			
			@Override
			public String getAuthority() {
				
				return "ROLE_" + member.getRole();
			}
		});
		
		return roleList;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		
		return member.getUsername();
	}

	@Override 
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	
}