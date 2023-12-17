package com.airline.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml"
						,"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})	
@Log4j
@WebAppConfiguration
public class BoardDiaryControllerTest {

	@Autowired
	private WebApplicationContext context;	
	
	private MockMvc mockMvc;	
	
	@Before			
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void listTest() throws Exception{
		log.info(
			mockMvc.perform(							
					MockMvcRequestBuilders.get("/boardDiary/list"))	//요청
					.andReturn()								//반환
					.getModelAndView()							//model과 view를 함께 반환
					.getModelMap()								//view 대신 값만 취해서 검토하겠다
		);
	}
	
	@Test
	@WithMockUser(roles = {"USER"})
	public void writeTest() throws Exception{
		String resultPage = mockMvc.perform(							
								MockMvcRequestBuilders.post("/boardDiary/write")
								.param("boardTitle", "mock title")
								.param("boardContent", "mock content")
								.param("boardWriter", "유저02"))	
							.andReturn()								
							.getModelAndView()							
							.getViewName();
			
		log.info("view name : " + resultPage);
	}
	
	@Test
	public void viewTest() throws Exception{
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/boardDiary/view").param("boardNum", "3"))	
						.andReturn()								
						.getModelAndView()							
						.getModelMap()								
			);
	}
	
	@Test
	@WithMockUser(username = "유저02", roles = "USER")	//writer를 지정해 줄 수 없음...
	public void updateTest() throws Exception{

		String resultPage = mockMvc.perform(							
								MockMvcRequestBuilders.post("/boardDiary/update")
								.param("boardTitle", "modify update title")
								.param("boardContent", "modify update content")
								.param("boardNum", "10"))	
							.andReturn()								
							.getModelAndView()							
							.getViewName();
			
		log.info("view name : " + resultPage);
	}
	
	@Test
	@WithMockUser(username = "유저02", roles = "USER")
	public void deleteTest() throws Exception{

		String resultPage = mockMvc.perform(							
								MockMvcRequestBuilders.post("/boardDiary/delete")
								.param("boardNum", "11"))	
							.andReturn()								
							.getModelAndView()							
							.getViewName();
			
		log.info("view name : " + resultPage);
	}

}
