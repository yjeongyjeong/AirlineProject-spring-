package com.airline.service;

public interface PayService {

	public int chargePoint(String userid, int amount);

	public int updatePoint(int point, String userid);

	public int updateKakaoPoint(int kakao, String userid);
	//
}
