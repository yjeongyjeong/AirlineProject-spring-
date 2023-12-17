package com.airline.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.Criteria;
import com.airline.vo.FlightResVO;
import com.airline.vo.KakaoUserVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class UserMapperTest {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserMapper mapper;
	
	@Test
	public void getUserTest() {
		KakaoUserVO vo = mapper.getUser("dbswjd4991@naver.com");
		log.info(vo);
		vo.getAuthority().forEach(auth->log.info(auth));
	}
	
	@Test
	public void getUserTes2t() {
		Criteria cri = new Criteria();
		mapper.getUserRes("user10", cri).forEach(board->log.info(board));
	}
	
	/*
	 * @Test public void queryTest() { log.info(mapper.getq()); }
	 */
	
	@Test
	public void updateUserInfoTest() {
		String userId = "dbswjd4991@naver.com";
		//String userNick = "윤정윤정";
		String userNameK = "윤정윤정";
		String userNameE = "YUN";
		String phone = "010-1111-1111";
		int postCode = 55555;
		String address = "경기도 수원시";
		mapper.updateUserInfo(userId, userNameK, userNameE, phone, phone, postCode, address);
	}
	
	@Test
	public void updateUserPwd() {
		String userId = "user01";
		String pwd = "user01";
		pwd = passwordEncoder.encode(pwd);
		mapper.updateUserPwd(userId, pwd);
	}
	
	@Test
	public void updateUserNickTest() {
		String userId = "dbswjd4991@naver.com";
		String userNick = "변경테스트";
		mapper.updateUserNick(userId, userNick);
	}
}
