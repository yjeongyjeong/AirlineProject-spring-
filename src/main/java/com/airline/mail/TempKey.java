package com.airline.mail;

import java.util.Random;

import lombok.extern.log4j.Log4j;

@Log4j
public class TempKey {
 
	public String getKey() {
		return authCodeMaker();
	}
	
	
	public String authCodeMaker() {
		String authCode = null;
		StringBuffer temp = new StringBuffer();
		Random rnd = new Random();
		
		for (int i = 0; i < 10; i++) {
			int rIndex = rnd.nextInt(3);
			switch (rIndex) {
			case 0:
				// a-z
				temp.append((char) ((int) (rnd.nextInt(26)) + 97));
				break;
			case 1:
				// A-Z
				temp.append((char) ((int) (rnd.nextInt(26)) + 65));
				break;
			case 2:
				// 0-9
				temp.append((rnd.nextInt(10)));
				break;
			}
		}

		authCode = temp.toString();
		log.info("생성된 랜덤키 >> " + authCode);

		return authCode;
	}
}
