package com.ustc.box.main.service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ustc.box.core.utils.ParamConstant;


/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */

@Repository
@Transactional
public class UserAccountService {
	private static final int PayAdd = 1;
	private static final int PayReduce = 0;
	
	private static final Logger LOGGER = LogManager
			.getLogger(UserAccountService.class);
  
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public Double getUserAccountRemaind(String tel){
		return jdbcTemplate.query("select balance from user_info where tel =? ",new Object[] {tel},new ResultSetExtractor<Double>(){
			@Override
			public Double extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				while(rs.next()) {  
	                   return rs.getDouble(1);  
	               }  
	              return null;  
			}
			
		});
	}
	
	public Boolean accountRemaindIsOverPrice(Double accountMount,Double price,Integer days){
		if(accountMount == null){
			return false;
		}else{
			if(accountMount < price*days){
				return false;
			}else{
				return true;
			}
		}
	}
	

	
	/**
	 * 扣除用户相应的钱
	 * @param parMoney
	 * @param tel
	 */
	public void deductMoney(Double parMoney,String tel,Integer recordId,String cabinetId,Integer cabinetNumber){
		String sql = "update user_info set balance=balance-"+parMoney+" where tel = '"+tel+"'";
		jdbcTemplate.execute(sql);
		recordPayDeduceMoney(tel, parMoney, recordId,cabinetId,cabinetNumber);
	}
	
	
	/**
	 * 返还上次扣除用户相应的钱
	 * @param parMoney
	 * @param tel
	 */
	public void addMoney(Double parMoney,String tel,Integer charType){
		LOGGER.info("更新用户:----"+tel+"---的账户,增加："+parMoney);
        Double money = queryUserMoney(tel);
        money +=parMoney;
		String sql = "update user_info set balance="+money+" where tel = '"+tel+"'";
		jdbcTemplate.execute(sql);
		recordPayAddMoney(tel,parMoney,charType);//1代表支付宝充值
	}
	
	
	/**
	 * 记录用户充值
	 * @param tel
	 * @param money
	 * @param type
	 */
	public void recordPayAddMoney(final String  tel,final Double money,final Integer chargeType){
		jdbcTemplate.update("insert into charge_record(tel,money,charge_type) values (?,?,?)", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, tel);
				ps.setDouble(2, money);
				ps.setInt(3, chargeType);
			}
		});
	}
	
	
	/**
	 * 记录用户消费
	 * @param tel
	 * @param money
	 * @param type
	 */
	public void recordPayDeduceMoney(final String tel, final Double money,
			final Integer record_id, final String cabinetId,
			final Integer cabinetNumber) {
		jdbcTemplate
				.update("insert into consume_record(tel,money,record_id,cabinetId,cabinetNumber) values (?,?,?,?,?)",
						new PreparedStatementSetter() {
							@Override
							public void setValues(PreparedStatement ps)
									throws SQLException {
								ps.setString(1, tel);
								ps.setDouble(2, money);
								ps.setInt(3, record_id);
								ps.setString(4, cabinetId);
								ps.setInt(5, cabinetNumber);
							}
						});
	}
	
	
	/**
	 * 记录用户取消
	 * @param tel
	 * @param money
	 * @param type
	 */
	public void recordPayCancel(Integer record_id) {
		jdbcTemplate
				.update("update consume_record set status = 1 where record_id = "+record_id);
	}
	
	
	/**
	 * 判断用户师傅有足够的钱打开该类型的格
	 * @param tel
	 * @param cabinetType
	 * @param days
	 * @return
	 */
	public Boolean isHasEnoughMoney(String tel,Double price,Integer days){
		if(price == null){
			return true;
		}
		Double accountMount = this
				.getUserAccountRemaind(tel);
		
		Boolean isHasMoney = this
				.accountRemaindIsOverPrice(accountMount,
						price,days);
		return isHasMoney;
	}
	
	
	/**
	 * 支付充值记录
	 */
	public void saveUserPayMoneyInfo(final Map<String,String> param){
	 String subject = param.get("subject");
	 final String[] res = subject.split("&");
	jdbcTemplate.update("insert into pay_record(out_trade_no,trade_no,trade_status,buyer_id,buyer_email,gmt_payment,total_fee,userTel,pay_balance) values (?,?,?,?,?,?,?,?,?)", new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, param.get("out_trade_no"));
				ps.setString(2, param.get("trade_no"));
				ps.setString(3, param.get("trade_status"));
				ps.setString(4, param.get("buyer_id"));
				ps.setString(5, param.get("buyer_email"));
				ps.setString(6, param.get("gmt_create"));
				ps.setDouble(7, Double.valueOf(param.get("total_fee")));
				ps.setString(8, res[0]);
				ps.setDouble(9, Double.valueOf(res[1]));
			}
		});
	
		  //更新用户账户
		  this.addMoney(Double.valueOf(res[1]),res[0],1);
	}
	
	public List<Map<String,Object>> queryPayRecord(String tel){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT"
		+" c.tel,"
		+" c.money,"
		+" c.addDate,"
		+" c.record_id,"
		+" ri.expressNumber,"
		+" ri.pickupDate,"
		+" ri.deliveryDate, "
	    +" ri.receiveTel,"
	    +" ui.company,"
	    +" CONCAT(bi.cabinetName,ri.boxId,'格') as boxaddress"
	    +" FROM"
		+" consume_record c,record_info ri,user_info ui,box_info bi where ri.id = c.record_id and ui.tel = ri.deliveryTel and bi.cabinetId = ri.cabinetId"
	    +" and"
		+" c.tel = ? and c.status = 0"
	    +" ORDER BY"
		+" c.addDate DESC");
			return jdbcTemplate.queryForList(sb.toString(),tel);
		}
	
	
	public List<Map<String,Object>> queryChargeRecord(String tel){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT"
		+" c.tel,"
		+" c.money,"
		+" c.addDate"
	    +" FROM"
		+" charge_record c where c.charge_type >= 0 "
		+" and c.tel = ?"
	    +" ORDER BY"
		+" c.addDate DESC");
			return jdbcTemplate.queryForList(sb.toString(),tel);
		}
	
	
	public Double queryPayTot(String tel){
		StringBuilder sb = new StringBuilder();
		sb.append("select sum(money) FROM consume_record where tel = '"+tel+"'");
		
		return jdbcTemplate.query(sb.toString(),new ResultSetExtractor<Double>(){
			@Override
			public Double extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				while(rs.next()) {  
	                    return rs.getDouble(1);  
	               }  
	              return null;  
			}
			
		});
}
	
	/**
	 * 得到用户账户余额
	 * @param tel
	 * @return
	 */
	public Double queryUserMoney(String tel){
		StringBuilder sb = new StringBuilder();
		sb.append("select balance FROM user_info where tel = '"+tel+"'");
		
		return jdbcTemplate.query(sb.toString(),new ResultSetExtractor<Double>(){
			@Override
			public Double extractData(ResultSet rs) throws SQLException,
					DataAccessException {
				while(rs.next()) {  
	                    return rs.getDouble(1);  
	               }  
	              return null;  
			}
			
		});
}
}