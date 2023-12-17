package com.airline.security;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.KakaoUserVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class UserTest {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private DataSource datasource;
	
	@Test
	public void adminInsertTest() {
		String sql = "insert into kakaouser(userid, userNick, userNameK, userNameE, pwd, mail, phone, postCode, address, userReginumFirst, userReginumLast) "
				+ "	values('admin', '관리자', '관리자', 'admin', ?, 'admin@naver.com', 00011112222, 11111, '서울시 용산구', 881231, 1111111)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, passwordEncoder.encode("admin"));
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	@Test
	public void userInsertTest() {
		String sql = "insert into kakaouser(userid, userNick, userNameK, userNameE, pwd, mail, phone, postCode, address, userReginumFirst, userReginumLast) "
				+ "	values('user01', '유저01', '김유저', 'userkim', ?, 'user01@naver.com', 00011112222, 11111, '서울시 용산구', 881231, 1111111)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, passwordEncoder.encode("user01"));
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Test
	public void adminInsertAuthTest() {
		String sql = "insert into authorities values(?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "admin");
			ps.setString(2, "ROLE_ADMIN");
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch (Exception e) {
				e.printStackTrace();
			
			}
		}
	}
	
	@Test
	public void userInsertAuthTest() {
		String sql = "insert into authorities values(?,?)";
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "user01");
			ps.setString(2, "ROLE_MEMBER");
			ps.executeUpdate();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch (Exception e) {
				e.printStackTrace();
			
			}
		}
	}
	
	@Test
	public void userLoginTest() {
		String sql = "select pwd from kakaouser where userid=?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = datasource.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, "test11");
			rs = ps.executeQuery();
			
			if(rs.next()) {
			
				String rawPassword = "W3xBl9m4f1";
				String encodedPassword = passwordEncoder.encode("W3xBl9m4f1");
			log.info("passwordEncoder한 값과 raw값 매치되는지 확인 >> " + passwordEncoder.matches(rawPassword, encodedPassword));
				if(passwordEncoder.matches(rawPassword, encodedPassword))log.info("로그인 성공");
				else {log.info("로그인 실패");}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps!=null)ps.close();
				if(con!=null)con.close();
			}catch (Exception e) {
				e.printStackTrace();
			
			}
		}
	}
		
	
}
