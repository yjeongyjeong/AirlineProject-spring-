package com.airline.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardQnaVO;
import com.airline.vo.Criteria;
import com.airline.vo.KakaoUserVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardQnaMapperTests {

	@Autowired
	private BoardQnaMapper mapper;
	
	@Test
	public void getList() {
		mapper.getList().forEach(list -> log.info(list));
	}
	
	@Test
	public void getPageList() {
		Criteria cri = new Criteria();  
		cri.setPageNum(1); 
		cri.setAmount(10);
		mapper.getPageList(cri); 
	}
	
	@Test
	public void getUpdateCount() {
		mapper.updateReadCount(6);
	}
	
	@Test
	public void readOne() {
		mapper.readOne(3);
	}
	
	@Test
	public void deleteQna() {
		mapper.deleteQna(3);
	}
	
	@Test
	public void updateQna() {
		BoardQnaVO vo = BoardQnaVO.builder()
				.boardcontent("업뎃테스트")
				.boardnum(4)
				.build();
		mapper.updateQna(vo);
	}
	
	@Test
	public void registerQna() {
		BoardQnaVO vo = BoardQnaVO.builder()
				.boardsubject("레지테스트")
				.boardcontent("레지테스트")
				.boardwriter("관리자")
				.boardreref(17)
				.build();
		mapper.registerQna(vo);
	}
	
	@Test
	public void replyQna() {
		BoardQnaVO vo = BoardQnaVO.builder()
				.boardsubject("답변")
				.boardcontent("답변")
				.boardwriter("관리자")
				.boardreref(17)
				.boardrelevel(2)
				.boardreseq(1)
				.build();
		mapper.replyQna(vo);
	}

	
	@Test
	public void answer() {
		KakaoUserVO vo = new KakaoUserVO();
		vo.setUserId("user01");
		mapper.myAnsweredList(vo);
	}
	
	
}
