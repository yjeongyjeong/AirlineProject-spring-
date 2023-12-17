package com.airline.mapper;

import java.util.List;

import org.junit.Test; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardDiaryReplyVO;
import com.airline.vo.BoardDiaryVO;
import com.airline.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class BoardDiaryReplyMapperTest {
	
	@Autowired
	private BoardDiaryReplyMapper mapper;
	
	@Test
	public void insertReplyTest() {
		BoardDiaryReplyVO vo = new BoardDiaryReplyVO();
		vo.setBoardNum(3);
		vo.setReplyWriter("관리자");
		vo.setReplyContent("test03");
		int result = mapper.insertReply(vo);
	
		log.info("result : " + result);
	}
	
	@Test
	public void replyCountTest() {
		int replyCount = mapper.replyCount(3);
		
		log.info("reply count : " + replyCount);
	}
	
	@Test
	public void selectAllReplyTest() {
		Criteria cri = new Criteria();
		cri.setPageNum(2);
		cri.setAmount(2);
		
		List<BoardDiaryReplyVO> list = mapper.selectAllReply(cri, 3);
		
		mapper.selectAllReply(cri, 3).forEach(reply->log.info(reply));
	}
	
	@Test
	public void updateReplyTest() {
		BoardDiaryReplyVO vo = new BoardDiaryReplyVO();
		vo.setReplyNum(1);
		vo.setReplyContent("update01");
		boolean result = mapper.updateReply(vo)==1;
		
		log.info("update done? : " + result);
	}
	
	@Test
	public void deleteReplyTest() {
		boolean result = mapper.deleteReply(3)==1;
		log.info("delete done? : " + result);
	}
	
	@Test
	public void selectOneReplyTest() {
		BoardDiaryReplyVO vo = mapper.selectOneReply(2);
		
		log.info("get one : " + vo);
	}

}
