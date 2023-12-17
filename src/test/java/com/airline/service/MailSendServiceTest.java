package com.airline.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.mail.MailHandler;
import com.airline.mail.TempKey;
import com.airline.mapper.MailSendMapper;
import com.airline.mapper.MailSendMapperTest;

import lombok.extern.log4j.Log4j;
@RunWith(SpringJUnit4ClassRunner.class) 
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MailSendServiceTest {

	@Autowired
	private MailSendService mailSendService;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Test
	public void updateMailKeyTest() throws Exception{
		String mail_key = new TempKey().getKey(); //랜덤키 받아서 저장
		String email = "dbswjd4991@naver.com";
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("email", email);
		params.put("mail_key", mail_key);
		
		log.info("BEFORE MAPPER");
		mailSendService.modifyMailKey(params); //email 조건으로 랜덤키 컬럼에 저장
		log.info("AFTER MAPPER");

		MailHandler sendMail = new MailHandler(mailSender);
		sendMail.setSubject("카카오 항공 인증 메일입니다.");
		sendMail.setText("<h3>카카오 항공을 찾아주셔서 감사합니다.</h3>" +
						"<br>아래 확인 버튼을 눌러서 인증을 완료해 주시기 바랍니다." +
						"<br><br><a href='http://localhost:8090/join/getUserId"
						+"?email="+email+
						"&mail_key="+mail_key
						+"' target='_blank'>이메일 인증 확인</a>");
		sendMail.setFrom("systemlocal99@gmail.com", "카카오 항공");
		sendMail.setTo(email);
		sendMail.send();
		
		log.info("Service Test >>> updateMailKeytest end");
	}
	


}
