package com.airline.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardEventVO;
import com.airline.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class BoardEventMapperTest {

	@Autowired
	private BoardEventMapper mapper;
	
	@Test
	public void getListTest() {
		mapper.getList().forEach(board->log.info(board));
	}
	
	@Test
	public void getListwithPagingTest() {
		Criteria cri = new Criteria();
		cri.setPageNum(1);
		cri.setAmount(2);
		
		cri.setType("boardTitle");
		cri.setKeyword("test");
		
		mapper.getListwithPaging(cri).forEach(board->log.info(board));
	}
	
	@Test
	public void getListPastEventTest() {
		Criteria cri = new Criteria();
		mapper.getListPastEvent(cri).forEach(board->log.info(board));
	}
	
	@Test
	public void getTest() {
		BoardEventVO vo = mapper.get(3);
		log.info("Event VO : "+vo);
	}
	
	@Test
	public void insertTest() {
		BoardEventVO vo = new BoardEventVO();
		vo.setBoardTitle("test5");
		vo.setBoardContent("test content5");
		vo.setStartDate("2023-12-01 00:50:00");
		vo.setEndDate("2023-12-10 20:00:00");
				
		int insert = mapper.insert(vo);
		log.info("insert result : " + insert);
	}
	
	@Test
	public void updateTest() {
		BoardEventVO vo = new BoardEventVO();
		vo.setBoardTitle("test update1");
		vo.setBoardContent("test content update1");
		vo.setStartDate("2023-12-02 00:50:10");
		vo.setEndDate("2023-12-11 20:00:10");
		vo.setBoardNum(3);
		
		int result = mapper.update(vo);
		log.info("update result : " + result);
	}
	
	@Test
	public void deleteTest() {
		int delete = mapper.delete(5);
		log.info("delete result : " + delete);
	}
	
	@Test
	public void getTotalCountTest() {
		Criteria cri = new Criteria();
		cri.setType("boardContent");
		cri.setKeyword("update");
		int totalCount = mapper.getTotalCount(cri);
		
		log.info("total count : " + totalCount);
	}
	
	@Test
	public void updateReadCountTest() {
		int result = mapper.updateReadCount(3);
		log.info("result : " + result);
	}
	
	@Test
	public void getListOverDueTest() {
		mapper.getListOverDue("2025-01-01").forEach(board->log.info(board));
	}
	
	@Test
	public void updateOngoingTest() {
		int result = mapper.updateOngoing(76);
		log.info("result : " + result);
	}

}
