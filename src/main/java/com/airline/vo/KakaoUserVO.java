package com.airline.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KakaoUserVO {
	private String userId;
	private String userNick;
	private int gradeCode;
	private String userNameK;
	private String userNameE;
	private String gender;
	private String pwd;
	private String mail ;
	private String phone; //int에서 -> char(13)으로 변경함
	private int postCode;
	private String address;
	private int userReginumFirst;
	private int userReginumLast;
	private int admin;
	private int enabled;

	private String mail_key; //이메일 인증을 위함
	
	//private String authority;
	
	//스프링 시큐리티 권한 테이블
	private List<AuthorityVO> authority;

}
