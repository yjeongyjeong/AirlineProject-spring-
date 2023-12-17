package com.airline.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.airline.vo.KakaoUserVO;

public interface MailSendMapper {

	public void updateMailKey(Map<String, String> params);
	//email을 받아서 컬럼에 생성된 mail_key를 입력 
	//public KakaoUserVO checkedMailKey(String mail_key);
	public void resetMailKey(String email);
}
