package com.airline.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardDiaryVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardDiaryServiceImplTest {
	
	@Autowired
	private BoardDiaryService service;
	
	@Test
	public void getTest() {			//트랜잭션 동작 확인...
		log.info("get service impl test");
		BoardDiaryVO vo = service.get(3);
		log.info("vo >>> "+vo);
	}

}
