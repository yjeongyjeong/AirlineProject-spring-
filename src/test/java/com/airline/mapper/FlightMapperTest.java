package com.airline.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.airline.vo.Criteria;

import lombok.extern.log4j.Log4j;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/security-context.xml","file:src/main/webapp/WEB-INF/spring/root-context.xml"})
@Log4j
public class FlightMapperTest {
	
	@Autowired
	private FlightMapper mapper;
	
	@Test
	public void getListTest() {
		 mapper.getList(new Criteria()).forEach(vo->log.info(vo));
	}
	
	@Test
	public void getSearchTest() {
		mapper.getListSearch(new Criteria(),"김포","제주", "2024-02-28")
		.forEach(vo->log.info(vo));
	}
	
	@Test
	public void getSearchByFlightNameTest() {
		mapper.getListSearchByFlightName(new Criteria(),"OZ8234", "2023-12-01")
		.forEach(vo->log.info(vo));
	}
	
	@Test
	public void getTotalSearchByFlightNameTest() {
		log.info(mapper.getTotalSearchByFlightName(new Criteria(),"OZ8234", "2023-12-01"));
	}
	
	@Test
	public void getRoundTripPriceTest() {
		log.info(mapper.getRoundTripPrice("홍콩", "인천"));
	}
	
	@Test
	public void getDistinctDepTest() {
		List<String> list = mapper.getDistinctDep("","1");
		for(String str : list) {			
			log.info(str);
		}
	}
	
	@Test
	public void getDistinctArrByDepTest() {
		List<String> list = mapper.getDistinctArrByDep2("인천","","2");
		for(String str : list) {			
			log.info(str);
		}
	}
	
	@Test
	public void getClosestFlightPrevTest() {
		log.info(mapper.getClosestFlightPrev("홍콩", "인천", "2023-12-08"));
	}
	
	@Test
	public void getClosestFlightafterTest() {
		log.info(mapper.getClosestFlightAfter("인천", "타슈켄트", "2023-12-15"));
	}
	
	@Test
	public void getDistinctArrRegionCodeTest() {
		log.info(mapper.getDistinctArrRegionCode("김포"));
	}
	
	@Test
	public void updateSeatCountTest() {
		int updateSeatCount = mapper.updateSeatCount("OZ740","2024-02-28");
		log.info(mapper.updateSeatCount("OZ740","2024-02-28"));
		log.info(">>>>"+updateSeatCount);
	}
	
	//검색어만 하는건 성공
	/*
	 * @Test public void getSearchTest2() { mapper.getListSearch2("인천","오사카",
	 * "2022-12-01") .forEach(vo->log.info(vo)); }
	 */
	

}
