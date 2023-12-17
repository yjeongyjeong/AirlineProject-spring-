package com.airline.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardQnaVO;
import com.airline.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardQnaServiceImplTests {

	@Autowired
	private BoardQnaService service;
	
	@Test
	public void getList() {
		service.getList().forEach(list -> log.info(list));
	}
	
	@Test
	public void getPageList() { 
		Criteria cri = new Criteria();  
		cri.setPageNum(2);
		cri.setAmount(2);
		service.getPageList(cri);
	}
	
	@Test
	public void getTotal(Criteria cri) {
		service.getTotal(cri);
	}

	@Test
	public void readOne() {
		service.readOne(5);
	}
	
	@Test
	public void deleteQna() {
		service.deleteQna(2);
	}
	
	@Test
	public void updateQna() {
		BoardQnaVO vo = BoardQnaVO.builder()
				.boardcontent("두두두")
				.boardnum(5)
				.build();
		
		service.updateQna(vo);
	}
	
	@Test
	public void registerQna() {
		BoardQnaVO vo = BoardQnaVO.builder()
				.boardsubject("하이")
				.boardcontent("바이")
				.boardwriter("user01")
				.build();
		
		service.registerQna(vo);
	}
	
	
	
	
	
	
}
