package com.airline.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.airline.mapper.PayMapper;

@Service
public class PayServiceImpl implements PayService {

	@Autowired
	private PayMapper mapper;
	
	@Override
	public int chargePoint(String userid,int amount) {
		return mapper.chargePoint(userid, amount);
	}

	@Override
	public int updatePoint(int point,String userid) {
		return mapper.updatePoint(point, userid);
	}

	@Override
	public int updateKakaoPoint(int kakao,String userid) {
		return mapper.updateKakao(kakao, userid);
	}
	//

}
