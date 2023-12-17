package com.airline.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.airline.mail.MailHandler;
import com.airline.mail.TempKey;
import com.airline.mapper.MailSendMapper;
import com.airline.vo.KakaoUserVO;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class MailSendServiceImpl implements MailSendService{

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	private MailSendMapper mailSendMapper;

	@Override
	public void modifyMailKey(Map<String, String> params) throws Exception {
		mailSendMapper.updateMailKey(params);
	}

	@Override
	public void removeMailKey(String email) throws Exception {
		mailSendMapper.resetMailKey(email);
	}


	


}
