package com.airline.persistence;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class DataSourceTest {
	@Autowired
	private DataSource dataSource; //root-context에 id와 동일! (거기서 받아옴)
	//hikariDataSource
	@Test
	public void testConnection2() {
		try {
			Connection conn = dataSource.getConnection(); //객체가 만들어졌는지 확인가능
			log.info(conn); //객체가 만들어졌는지 확인가능
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}
}
