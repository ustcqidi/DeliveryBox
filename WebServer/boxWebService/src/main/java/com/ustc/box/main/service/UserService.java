package com.ustc.box.main.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustc.box.core.dao.HDao;
import com.ustc.box.core.utils.EhcacheHelper;
import com.ustc.box.core.utils.SecureUtils;
import com.ustc.box.main.vo.UserResponse;

/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */

@Repository
@Transactional
public class UserService {
  
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public void saveUserBaseInfo(final UserResponse user){
		jdbcTemplate.update("insert into user_info(password,tel,type,imei,token) values (?,?,?,?,?)", new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, user.getPassword());
				ps.setString(2, user.getTel());
				ps.setString(3, user.getType());
				ps.setString(4, user.getImsi());
				ps.setString(5, user.getToken());
			}
		});
	}
	
	public void updateUserBaseInfo(final UserResponse user){
		
		StringBuilder sb = new StringBuilder();
		sb.append("update  user_info set ");
		if(StringUtils.isNotEmpty(user.getTel())){
			sb.append("tel ='"+user.getTel()+"'");
		}
		if(StringUtils.isNotEmpty(user.getType())){
			sb.append(",type ='"+user.getType()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getImei())){
			sb.append(",imei ='"+user.getImei()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getPassword())){
			sb.append(",password ='"+user.getPassword()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getToken())){
			sb.append(",token ='"+user.getToken()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getCourier())){
			sb.append(",courier ='"+user.getCourier()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getCompany())){
			sb.append(",company ='"+user.getCompany()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getUsername())){
			sb.append(",username ='"+user.getUsername()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getDelivery_price_desc())){
			sb.append(",delivery_price_desc ='"+user.getDelivery_price_desc()+"'");
		}
		
		if(StringUtils.isNotEmpty(user.getAvatar_url())){
			sb.append(",logo ='"+user.getAvatar_url()+"' ");
		}
		
		sb.append("  where tel = '"+user.getTel()+"'");
		jdbcTemplate.update(sb.toString());
	}
	
//	public void updateUserInfo(final UserResponse user){
//		jdbcTemplate.update("update  user_info set logo = ?,type = ?,courier=?," +
//				" company = ?, username = ?,delivery_price_desc = ? where tel = ?", new PreparedStatementSetter() {
//			@Override
//			public void setValues(PreparedStatement ps) throws SQLException {
//				ps.setString(1, user.getAvatar_url());
//				ps.setString(2, user.getType());
//				ps.setString(3, user.getCourier());
//				ps.setString(4, user.getCompany());
//				ps.setString(5, user.getUsername());
//				ps.setString(6, user.getDelivery_price_desc());
//				ps.setString(7, user.getTel());
//			}
//		});
//	}
//	
	
	public  UserResponse getUserInfoByTel(String tel){
		final UserResponse user = new UserResponse();
		jdbcTemplate.query("select u.password,u.tel ,u.type,u.imei,u.token,u.courier," +
				"u.company,u.username,u.logo,u.balance,u.delivery_price_desc from " +
				" user_info u  where u.tel  = '"+tel+"'", new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) throws SQLException {
				user.setPassword(rs.getString(1));
				user.setTel(rs.getString(2));
				user.setType(rs.getString(3));
				user.setImei(rs.getString(4));
				user.setToken(rs.getString(5));
				user.setCourier(rs.getString(6));
				user.setCompany(StringUtils.isEmpty(rs.getString(7))?"":rs.getString(7));
				user.setUsername(StringUtils.isEmpty(rs.getString(8))?"":rs.getString(8));
				user.setAvatar_url(StringUtils.isEmpty(rs.getString(9))?"":rs.getString(9));
				user.setBalance(StringUtils.isEmpty(rs.getString(10))?"":rs.getString(10));
				user.setDelivery_price_desc(StringUtils.isEmpty(rs.getString(11))?"":rs.getString(11));
			}
		});
		return user;
	}
	
	public void deleteUserByBaseInfo(final UserResponse user){
		jdbcTemplate.execute("delete user_info where tel = '"+user.getTel()+"'");
	}
	
	public int isExistedNumber(String tel){
		return jdbcTemplate.queryForInt("select count(*) from user_info where tel =  ? ", tel);
	}
	public int getUserByToken(String token,String tel){
		return jdbcTemplate.queryForInt("select count(*) from user_info where token = ? and tel = ? ", token,tel);
	}
	
	public String queryTokenByTel(String tel){
		return jdbcTemplate.query("select token from user_info where tel =  ? ",new Object[] {tel},new ResultSetExtractor<String>(){

			@Override
			public String extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				while(rs.next()) {  
	                   return rs.getString(1);  
	               }  
	              return null;  
			}
			
		});
	}
	
	
	public String loginTel(UserResponse user){
		    String token = SecureUtils.getTokenInfo();
			user.setToken(token);
			UserResponse up = getUserInfoByTel(user.getTel());
			if(up.getTel() == null){
				this.saveUserBaseInfo(user);
			}else{
				up.setToken(token);
				this.updateUserBaseInfo(up);
			}
			return token;
	}
	
	/**
	 * 是否需要完善资料
	 * @param user
	 * @return
	 */
	public Boolean isNeedComplete(UserResponse user) {
		return StringUtils.isEmpty(user.getUsername())
				|| StringUtils.isEmpty(user.getCompany());
	}
	
}
