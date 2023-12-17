package com.airline.mapper;

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
public class BoardNoticeMapperTests {

	@Autowired
	private BoardNoticeMapper mapper;
	
	@Test
	public void testGetList() {
		mapper.getList().forEach(list -> log.info(list));
	}
	
	@Test
	public void testGetUserList() { 
		mapper.getUserList().forEach(user -> log.info(user));
	} 
	  
	@Test
	public void testGetUser() {
		log.info(mapper.getUser("admin"));
	}
	
	@Test
	public void testGetPageList() {
		Criteria cri = new Criteria();
		cri.setKeyword("awd");
		cri.setType("T");
		cri.setPageNum(3);
		cri.setAmount(10);
		mapper.getPageList(cri);
	}
	
	@Test
	public void testgetTotal() {
		Criteria cri = new Criteria();
		cri.setType("T");
		cri.setKeyword("우아아");
		mapper.getTotal(cri);
		
	}
	
	@Test 
	public void getOne() { 
		mapper.getOne(5);
	}
	
	@Test
	public void insert() {
		BoardNoticeVO vo = BoardNoticeVO.builder()
				.boardsubject("ggg")
				.boardcontent("aaa")
				.boardwriter("user01")
				.build();
		mapper.insert(vo);
	}
	
	@Test
	public void modify() {
		BoardNoticeVO vo = BoardNoticeVO.builder()
				.boardcontent("ggg")
				.boardnum(9)
				.build();
		mapper.modify(vo);
	}
	
	@Test
	public void delete() {
		mapper.delete(13);
	}
	

}
