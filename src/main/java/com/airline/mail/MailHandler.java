package com.airline.mail;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailHandler { //메일전송 라이브러리 setter
	//메일 제목, 내용, 발송자, 수신자, 보내기(send)
	
	private JavaMailSender mailSender; //MimeMessage는 html가능(멀티파트 데이터를 처리)
	private MimeMessage message;
	private MimeMessageHelper messageHelper;
	
	public MailHandler(JavaMailSender mailSender) throws MessagingException{
		this.mailSender = mailSender;
		message = this.mailSender.createMimeMessage();
		messageHelper = new MimeMessageHelper(message, true, "utf-8");
	}
	
	public void setSubject(String subject) throws MessagingException{
		messageHelper.setSubject(subject);
		//mail content
	}
	
	public void setText(String htmlContent) throws MessagingException{
		messageHelper.setText(htmlContent, true);
	}
	
	public void setFrom(String email, String name) throws UnsupportedEncodingException, MessagingException{
		messageHelper.setFrom(email, name);
	}
	
	public void setTo(String email) throws MessagingException {
		messageHelper.setTo(email);
	}
	
	public void addInline(String contentId, javax.activation.DataSource dataSource) throws MessagingException{
		messageHelper.addInline(contentId, dataSource);
	}
	
	public void send() {
		mailSender.send(message);
	}
}
