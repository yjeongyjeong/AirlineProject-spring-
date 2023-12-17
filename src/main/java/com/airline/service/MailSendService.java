package com.airline.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

import com.airline.vo.KakaoUserVO;

public interface MailSendService {

	public void modifyMailKey(Map<String, String> params) throws Exception;
	public void removeMailKey(String email) throws Exception;
	
	
}
