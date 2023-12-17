package com.airline.mapper;

import org.junit.Test; 
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.BoardDiaryVO;
import com.airline.vo.Criteria;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class BoardDiaryLikeMapperTest {
	
	@Autowired
	private BoardDiaryLikeMapper mapper;
	
	@Test
	public void insertLikeTest() {
		int result = mapper.insertLike(3, "user01");
		log.info("result : " + result);
	}
	
	@Test
	public void deleteLikeTest() {
		int result = mapper.deleteLike(3, "user01");
		log.info("result : " + result);
	}
	
	
	@Test
	public void checkLikeTest() {
		boolean check = mapper.checkLike(3, "user01") == 1;
		log.info("like? : " + check);
	}
	
	@Test
	public void likecountTest() {
		int likeCount = mapper.likeCount(3);
		log.info("like count : " + likeCount);
	}
	
	

}
