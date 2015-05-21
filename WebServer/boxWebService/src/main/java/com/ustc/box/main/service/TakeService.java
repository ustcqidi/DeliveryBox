package com.ustc.box.main.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ustc.box.core.utils.PageUtils;
import com.ustc.box.main.vo.PickupConstant;


/**
 * @author gpzhang
 *
 * @date : 2014-11-28
 */

@Repository
@Transactional
public class TakeService {
  
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	
	public List<Map<String,Object>> queryDeliving(String tel,List<Integer> pickupType,Integer type,String page){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ri.boxId,ri.expressNumber,ri.receiveTel,ri.deliveryTel,ui.username,ud.company,ri.deliveryDate,ri.pickupDate,CONCAT(bi.cabinetName,bc.cabinetNumber,'格') as boxaddress" +
				" ,datediff(now(),ri.deliveryDate) as exceedDays FROM " +
				"record_info ri,box_cabinet bc,box_info bi,user_info ui, user_info ud WHERE ri.cabinetId = bc.cabinetId AND ri.boxId = bc.cabinetNumber" +
				" AND ri.cabinetId = bi.cabinetId AND ui.tel = ri.receiveTel and ud.tel = ri.deliveryTel and ri.receiveTel = '"+tel+"' " +
				" and ri.pickupType in ");
		    sb.append("("+StringUtils.join(pickupType, ",")+")");
		    if(type == 0){//正在派件
		    	sb.append(" order by  ri.pickupDate  asc");
		    }else{
		    	sb.append(" order by  ri.pickupDate  desc");
		    }
		    if(StringUtils.isNotEmpty(page)){
		    	  sb.append(PageUtils.getPageSQL(Integer.parseInt(page)));
		    }
		  return jdbcTemplate.queryForList(sb.toString());
		
	}
	
	
/*	public List<Map<String,Object>> queryDelived(String tel){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ri.boxId,ri.expressNumber,ri.receiveTel,ui.username,ri.deliveryDate ,CONCAT(bi.communityAddress,bi.cabinetSort,'号柜子','第',bc.cabinetNumber,'格子') as boxaddress" +
				" ,datediff(now(),ri.deliveryDate) as exceedDays FROM " +
				"record_info ri LEFT JOIN box_cabinet bc ON ri.boxId = bc.id " +
				"LEFT JOIN box_info bi ON bi.cabinetId = bc.cabinetId LEFT JOIN" +
				" user_info ui ON ui.tel = ri.receiveTel WHERE ri.receiveTel = ? and ri.pickupType =1 and ri.deliveryIsConfirm = 1");
			sb.append(" order by  ri.pickupDate  desc");
		return jdbcTemplate.queryForList(sb.toString(),tel);
		
	}*/
	
	
	
	
	/**
	 * 得到该用户延期的天数和用于判断用户有没扫描错柜子
	 * @param tel
	 * @param cabinetId
	 * @param boxId
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Integer getOverDayCount(String tel,String cabinetId,Integer boxId){
		try {
			 return jdbcTemplate.queryForInt("select IFNULL(datediff(now(),deliveryDate),0) from record_info " +
						" where cabinetId = ? and boxId = ? and receiveTel = ? and pickupType = "+PickupConstant.confirm,cabinetId,boxId,tel);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * 更新状态，已经收取快递
	 * @param tel
	 * @param boxId
	 */
	public void pickedDeliveryBox(String tel,String cabinetId,Integer boxId,Integer  days,Double deliveryToMoney ,Integer pickUpType){
		jdbcTemplate.update("update record_info set  pickupToMoney=? ,dateCount = ? ,pickupType = ?,pickupDate = ? where" +
				" boxId = ? and cabinetId = ? and receiveTel = ? and pickupType = ?",deliveryToMoney,days,pickUpType,new Timestamp(System.currentTimeMillis())
		,boxId,cabinetId,tel,PickupConstant.confirm);
		jdbcTemplate.update("update box_cabinet set  cabinetIsBlank = 0 where" +
				" cabinetNumber = ?  and  cabinetId = ?",boxId,cabinetId);
	}
	
	
	/**
	 * 判断该用户是否有打开该柜子的权限
	 * @return
	 */
	public Boolean hasPermitOpenBox(String tel,String cabinetId,Integer boxId){
		Integer size =  jdbcTemplate.queryForInt("select count(*) from record_info where receiveTel = ?" +
				" and cabinetId = ? and  boxId = ? and pickupType = ?",tel,cabinetId,boxId,PickupConstant.confirm);
		if(size > 0 ){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * 得到别人的柜子
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public Integer getHelpBoxId(String tel,String cabinetId,String validatecode){
		Integer boxId = null;
		try{
		 boxId =  jdbcTemplate.queryForInt("select r.boxId from record_info r where " +
				"r.cabinetId = '"+cabinetId+"' and r.validatecode = '"+validatecode+"' and r.receiveTel = '"+tel+"' " +
				" and r.pickupType = "+PickupConstant.confirm);
		 return boxId;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 得到别人的柜子的发送验证码
	 * @return
	 */
	public List<String> getCodeList(String tel,String cabinetId){
		List<String> list =  jdbcTemplate.queryForList("select distinct(r.validatecode) from record_info r where " +
				"r.cabinetId = '"+cabinetId+"' and r.receiveTel = '"+tel+"' " +
				"and r.pickupType = "+PickupConstant.confirm,String.class);
		
		return list;
	}
	
	
	//获取用户最近两次使用过的柜子编号
	public List<String> getCabinetList(String tel){
		List<String> list =  jdbcTemplate.queryForList("select distinct(r.cabinetId) from record_info r where " +
				"  r.receiveTel = '"+tel+"' order by pickupDate desc limit 2 ",String.class);
		return list;
	}
	
	//得到附近的快递员信息,15天以内
	public List<Map<String,Object>> getNearDeliveryList(List<String> cabinetIds,String tel){
		List<Map<String,Object>> list =  jdbcTemplate.queryForList("SELECT DISTINCT (r.deliveryTel),u.username,u.company FROM record_info r LEFT JOIN user_info u ON u.tel = r.deliveryTel " +
				"WHERE r.cabinetId in( '"+StringUtils.join(cabinetIds,"','")+"') " +
				" and r.receiveTel = '"+tel+"' AND date(r.deliveryDate) >= date_sub(curdate(), INTERVAL 15 DAY)");
		return list;
	}
	
}
