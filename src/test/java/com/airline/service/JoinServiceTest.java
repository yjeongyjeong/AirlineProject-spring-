package com.airline.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.KakaoUserVO;

import lombok.extern.log4j.Log4j;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class JoinServiceTest {
	
	@Autowired
	private JoinService join;

//	@Test
//	public void confirmMemberTest() {
//		KakaoUserVO vo = KakaoUserVO.builder()
//				.userNameE("userchoi")
//				.userNameK("최유ㅇ저")
//				.gender("M")
//				.userReginumFirst(881231)
//				.userReginumLast(1111111)
//				.build();
//		
//		join.confirmMember(vo);
//		}
	
	@Test
	public void kakaoLoginTest() throws Throwable {
		join.getAccessToken("2W4AQCGwhnwzYQ8Z8ONcyJkOY0pPlZlNjmQ-u-93GZhgoQ-VcQpnIhzBLI4KPXUbAAABjBpMLq7_A_o_BVb6-Q");
		//responseCode : 200 이면 성공
	}

	
	@Test
	public void getUserInfoTest() throws Throwable {
		String access_Token = join.getAccessToken("jLLt_FTAeqsD0yieUoiLmCTQYGBUmPlazFwnWMak23P9d5pYZxMxa0Pti5AKKiVTAAABjCL3kvvkNSpXBP-m7Q");
		join.getUserInfo(access_Token);
	}

}
