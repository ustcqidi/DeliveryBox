package com.ustc.box.main.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ustc.box.core.dao.HDao;
import com.ustc.box.core.utils.DateUtils;
import com.ustc.box.main.vo.RequestMessage;
import com.ustc.box.main.vo.ResponseBase;


/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */

@Repository
@Transactional
public class LogService {
  
	
	@Resource
	private MongoTemplate mongoTemplate;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public void logRequestJsonInfo(String json){
		mongoTemplate.save(json, "requestJson");
	}
	
	public void logRequestMessage(RequestMessage rm){
		mongoTemplate.save(rm, "requestMsg");
	}
	
	public void logResponseMessage(String resultJson){
		mongoTemplate.save(resultJson, "responseJson");
	}
	
	public void recordOpenInfo(String cabinetId, Integer boxId, String type,
			String tel, Boolean status) {
        JsonObject json = new JsonObject();
        json.addProperty(cabinetId, cabinetId);
        json.addProperty(boxId.toString(), boxId);
        json.addProperty(type, type);
        json.addProperty(tel, tel);
        json.addProperty(status.toString(), status);
        json.addProperty("time", sdf.format(new Date()));
		mongoTemplate.save(json,"openInfo");
		
	}

}
