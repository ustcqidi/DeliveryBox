/**
 * Copyright 2015 iflytek.com
 * 
 * All right reserved
 *
 * creator : pengwu2
 */
package demo;

import java.io.InputStream;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ustc.box.dao.HDao;


/**
 * 广告曝光请求测试用例
 * 
 * @author pengwu2
 *
 */
@ContextConfiguration({ "classpath:spring/applicationContext-stat.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDb {

	@Resource
	private HDao dao;

	/**
	 * 测试曝光上报请求
	 * @throws Exception
	 */
	@Test
	public void testDb() throws Exception {

		 System.out.println(dao.find("from UserInfo"));
	}


}
