package com.airline.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.airline.vo.KakaoUserVO;

import lombok.Getter;

@Getter
public class CustomUser extends User {

	private static final long serialVersionUID = 1L;

	private KakaoUserVO user;

	public CustomUser(String userid, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		super(userid, password, authorities);
	}

	public CustomUser(KakaoUserVO vo) {
		super(vo.getUserId(), vo.getPwd(), vo.getAuthority()
				.stream()
				.map(auth->new SimpleGrantedAuthority(auth.getAuthority()))
				.collect(Collectors.toList()));
		this.user = vo;
	}

}
