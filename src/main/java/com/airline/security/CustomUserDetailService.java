package com.airline.security;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.airline.mapper.UserMapper;
import com.airline.vo.KakaoUserVO;

import lombok.Getter;
import lombok.extern.log4j.Log4j;

@Log4j
@Getter
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		log.warn("Load user by username >>" + username);
		
		KakaoUserVO vo = mapper.getUser(username); //id pwd nickname enabled authority
		
		log.warn("queried by member mapper : "+ vo);
		
		if(vo != null) {
//			grantedAuthorities.add(new SimpleGrantedAuthority(vo.getAuthority().get(0).getAuthority()));
			return new CustomUser(vo);
			
		}else {
			return null;
		}
		 
		//return vo == null? null : new CustomUser(vo);
		
		
	}

}





