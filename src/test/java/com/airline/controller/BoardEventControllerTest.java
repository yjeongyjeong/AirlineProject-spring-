package com.airline.controller;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BoardEventControllerTest {

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
					MockMvcRequestBuilders.get("/boardEvent/list"))	
					.andReturn()								
					.getModelAndView()							
					.getModelMap()								
		);
	}
	
	@Test
	@WithMockUser(roles = {"USER"})
	public void writeTest() throws Exception{
		String resultPage = mockMvc.perform(							
								MockMvcRequestBuilders.post("/boardEvent/write")
								.param("boardTitle", "mock title")
								.param("boardContent", "mock content")
								.param("startDate", "2023-11-30 02:00:00")
								.param("endDate", "2023-11-30 04:00:00"))	
							.andReturn()								
							.getModelAndView()							
							.getViewName();
			
		log.info("view name : " + resultPage);
	}
	
	@Test
	public void viewTest() throws Exception{
		log.info(
				mockMvc.perform(MockMvcRequestBuilders.get("/boardEvent/view").param("boardNum", "3"))	
						.andReturn()								
						.getModelAndView()							
						.getModelMap()								
			);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")	//writer를 지정해 줄 수 없음...
	public void updateTest() throws Exception{

		String resultPage = mockMvc.perform(							
								MockMvcRequestBuilders.post("/boardEvent/update")
								.param("boardTitle", "modify update title")
								.param("boardContent", "modify update content1")
								.param("startDate", "2023-11-30 09:00:00")
								.param("endDate", "2023-12-21 04:00:00")
								.param("boardNum", "7"))		
							.andReturn()								
							.getModelAndView()							
							.getViewName();
			
		log.info("view name : " + resultPage);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void deleteTest() throws Exception{

		String resultPage = mockMvc.perform(							
								MockMvcRequestBuilders.post("/boardEvent/delete")
								.param("boardNum", "8"))	
							.andReturn()								
							.getModelAndView()							
							.getViewName();
			
		log.info("view name : " + resultPage);
	}

}
