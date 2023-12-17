package com.airline.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardNoticeVO;
import com.airline.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml") 
@Log4j 
public class BoardNoticeServiceImplTest {

	@Autowired
	private BoardNoticeService service;
	
	@Test
	public void testGetList() {
		service.getList().forEach(list -> log.info(list)); 
	}
	
	@Test
	public void testGetUserList() {
		service.getUserList().forEach(user -> log.info(user));
	}
	
	@Test
	public void testGetUser() {
		service.getUser("user01");
	}
	
	@Test
	public void testGetPageList() {
		Criteria cri = new Criteria();
		cri.setPageNum(2);
		cri.setAmount(10);
		service.getPageList(cri);
	}

	@Test
	public void testGetOne() {
		service.getOne(6);
	}
	
	@Test
	public void getTotalTest() {
		Criteria cri = new Criteria();
		cri.setPageNum(1);
		service.getTotal(cri);
	}
	
	@Test
	public void testInsert() {
		BoardNoticeVO vo = BoardNoticeVO.builder()
				.boardsubject("wwwww")
				.boardcontent("adad")
				.boardwriter("admin")
				.build();
		service.insert(vo);
	}
	
	@Test
	public void testModify() {
		BoardNoticeVO vo = BoardNoticeVO.builder()
				.boardcontent("appapap")
				.boardnum(116)
				.build();
		service.modify(vo);
	}
	
	@Test
	public void testDelete() {
		service.delete(6);
	}
	
	
	@Test
	public void testPopup() { 
		Criteria cri = new Criteria();
		cri.setPageNum(1);
		cri.setAmount(10);
		service.noticePopup(cri);
	}
}
