package com.ustc.box.main.service;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustc.box.main.vo.UserResponse;


/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */

@Repository
@Transactional
public class BoxRegisterService {
  
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	public void registerBox(final String cabinetId,final String province, final String city,
			final String region,final String communityName,final String cabinetName,final String smallCount) {
		jdbcTemplate
				.update("insert into box_info(cabinetId,province,city,region," +
						"communityName,cabinetName,smallCount) " +
						"values (?,?,?,?,?,?,?)",
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, cabinetId);
								ps.setString(2, province);
								ps.setString(3, city);
								ps.setString(4, region);
								ps.setString(5, communityName);
								ps.setString(6, cabinetName);
								ps.setInt(7, Integer.parseInt(smallCount));
							}
						});
	}
	
	public void updateBox(final String cabinetId,final String province, final String city,
			final String region,final String communityName,final String cabinetName,final String smallCount) {
		jdbcTemplate
				.update("update  box_info set province = ?,"
						+ "city = ?,region=?,communityName = ?,cabinetName = ?,smallCount = ? where cabinetId = ?",
						new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, province);
								ps.setString(2, city);
								ps.setString(3, region);
								ps.setString(4, communityName);
								ps.setString(5, cabinetName);
								ps.setInt(6, Integer.parseInt(smallCount));
								ps.setString(7, cabinetId);
							}
						});
	}
	
	public void addbox_cabinet(final String cabinetId,final Integer cabinetType,final Integer cabinetNumber,
			final Integer cabinetIsBlank,final Integer type) {
		jdbcTemplate
				.update("insert into box_cabinet(cabinetId,cabinetType,cabinetNumber,cabinetIsBlank,type) values (?,?,?,?,?)",
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, cabinetId);
								ps.setInt(2, cabinetType);
								ps.setInt(3, cabinetNumber);
								ps.setInt(4, cabinetIsBlank);
								ps.setInt(5, type);
								
							}
						});
	}

	public void deletebox_cabinet(final String cabinetId){
		jdbcTemplate
				.update("delete from box_cabinet where cabinetId = ?",
						new PreparedStatementSetter() {

							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, cabinetId);
								
							}
						});
	}
	
	
	public Boolean isRegister(String cabinetId){
		Integer count = 0;
		try{
			count = jdbcTemplate.queryForInt("select count(*) from box_info where cabinetId = '"+cabinetId+"'");
		}catch (Exception e) {
			return false;
		}
		if(count > 0 ){
			return true;
		}else{
			return false;
		}
	}
}
