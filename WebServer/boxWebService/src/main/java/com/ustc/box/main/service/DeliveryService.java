package com.ustc.box.main.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.mysql.jdbc.Statement;
import com.ustc.box.core.dao.HDao;
import com.ustc.box.core.utils.PageUtils;
import com.ustc.box.main.vo.BoxInfo;
import com.ustc.box.main.vo.PickupConstant;
import com.ustc.box.main.vo.RecordInfo;

/**
 * @author gpzhang
 * 
 * @date : 2014-11-28
 */

@Repository
@Transactional
@SuppressWarnings("deprecation")
public class DeliveryService {

	@Resource
	private JdbcTemplate jdbcTemplate;

	@Resource
	private HDao hDao;

	public BoxInfo getDeliveryBox(String cabinetId, Integer cabinetType ) {
		StringBuffer sb = new StringBuffer();
		sb.append("select bc.cabinetNumber,");
		if(cabinetType == 1){
			sb.append("bi.dCabinetPrices");
		}else if(cabinetType == 2){
			sb.append("bi.zCabinetPrices");
		}else{
			sb.append("bi.xCabinetPrices");
		}
		
		sb.append(" from box_cabinet bc LEFT JOIN box_info bi on bi.cabinetId = bc.cabinetId  where  bc.status = 1 and bc.cabinetId = ?"
				+ " and bc.cabinetType = ? and bc.cabinetIsBlank = 0  ");
		return jdbcTemplate
				.query(sb.toString(),
						new Object[] { cabinetId, cabinetType },
						new ResultSetExtractor<BoxInfo>() {
							@Override
							public BoxInfo extractData(ResultSet rs)
									throws SQLException, DataAccessException {
								while (rs.next()) {
									BoxInfo boxInfo = new BoxInfo();
									boxInfo.setBoxId( rs.getInt(1));
									boxInfo.setPrice(rs.getDouble(2));
									return boxInfo;
								}
								return null;
							}

						});
	}
	
	

	/**
	 * 快递员自动打开上次未确认的格子
	 * @param tel
	 * @param cabinetId
	 * @return
	 */
	public Integer getNoConfirmDeliveryBox(String tel, String cabinetId) {
		return hDao
				.findBySQLUniqueResult(
						"select min(boxId) from record_info r where r.deliveryTel = ? and r.cabinetId = ?"
								+ " and  r.pickupType = "+PickupConstant.open,
						tel, cabinetId);
	}

	public Integer getCabinetType(String cabinetId, Integer boxId) {
		return jdbcTemplate.queryForInt(
				"select cabinetType from box_cabinet where  "
						+ " cabinetId = ? and  cabinetNumber = ? ", cabinetId,
				boxId);
	}

	/**
	 * 用户点击确认
	 * 
	 * @param tel
	 * @param cabinetId
	 * @param boxId
	 */
	public void pdateDeliveryBox(String tel, String cabinetId, Integer boxId,
			String validatecode) {
		jdbcTemplate
				.update("update record_info set pickupType = "+PickupConstant.confirm+", validatecode = '"
						+ validatecode
						+ "' where pickupType = "+PickupConstant.open+" and"
						+ " boxId = "
						+ boxId
						+ " and deliveryTel = '"
						+ tel
						+ "' and cabinetId = '" + cabinetId + "'");
	}

	/**
	 * 用户点击返回
	 * 
	 * @param tel
	 * @param cabinetId
	 * @param boxId
	 */
	public RecordInfo backDeliveryBox(String tel, String cabinetId, Integer boxId) {
		StringBuffer sb = new StringBuffer("select id,deliveryToMoney from  record_info  where pickupType = "+PickupConstant.open+" and" + " boxId = "
				+ boxId + " and deliveryTel = '" + tel + "' and cabinetId = '"
				+ cabinetId + "'");
		RecordInfo	recordInfo = jdbcTemplate
		.query(sb.toString(),
				new ResultSetExtractor<RecordInfo>() {
					@Override
					public RecordInfo extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							RecordInfo recordInfo = new RecordInfo();
							recordInfo.setId( rs.getInt(1));
							recordInfo.setDeliveryToMoney(rs.getDouble(2));
							return recordInfo;
						}
						return null;
					}

				});
		
		if(recordInfo == null){
        	return null;
        }
		
		jdbcTemplate.execute("update record_info set pickupType = "
				+ PickupConstant.cancel+ " where id = "+recordInfo.getId());
		jdbcTemplate.update("update box_cabinet set  cabinetIsBlank = 0 where"
				+ " cabinetNumber = ?  and  cabinetId = ?", boxId, cabinetId);
		return recordInfo;
	}

	public Boolean checkCabinetFull(String cabinetId) {
		Integer size = jdbcTemplate
				.queryForInt(
						"select count(bc.id) from box_cabinet bc left join "
								+ " box_info bi on bi.cabinetId = bc.cabinetId  where  bi.cabinetId = '"
								+ cabinetId + "' "
								+ " and  bc.cabinetIsBlank = 0", cabinetId);
		if (size > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断快递员是否已经打开了该柜子
	 * 
	 * @param tel
	 * @param boxId
	 * @return
	 */
	public Boolean checkHasOpened(String tel, String cabinetId, Integer boxId) {
		return jdbcTemplate.query(
				"select id from record_info where deliveryTel = ?"
						+ " and cabinetId = ? and  boxId = ? and pickupType = "
						+ PickupConstant.confirm, new Object[] { tel, cabinetId,
						boxId }, new ResultSetExtractor<Boolean>() {
					@Override
					public Boolean extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						while (rs.next()) {
							return true;
						}
						return false;
					}

				});

	}

	public Integer deliveryed(final String cabinetId, final Integer boxId,
			final String expressNumber, final String deliveryTel,
			final String receiveTel,final Double price) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			String sql = "insert into record_info(cabinetId,boxId,expressNumber,deliveryTel,deliveryDate,receiveTel,deliveryToMoney) "
					+ "values (?,?,?,?,?,?,?)";

			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, cabinetId);
				ps.setInt(2, boxId);
				ps.setString(3, expressNumber);
				ps.setString(4, deliveryTel);
				ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				ps.setString(6, receiveTel);
				ps.setDouble(7, price==null?0.0:price);
				return ps;
			}
		}, keyHolder);

		Long generatedId = keyHolder.getKey().longValue();
		return generatedId.intValue();
	}

	@Transactional
	public void updateCabinetStatus(String cabinetId, Integer boxId,
			Integer status) {
		jdbcTemplate.execute(" update box_cabinet set cabinetIsBlank = "
				+ status + " where cabinetId ='" + cabinetId
				+ "' and  cabinetNumber = " + boxId);
	}

	/**
	 * 更新快递员上次未确认的记录
	 * 
	 * @param cabinetId
	 * @param boxId
	 * @param status
	 */
	@Transactional
	public void updateRecordBack(String cabinetId, Integer boxId, String tel) {
		jdbcTemplate.execute("update record_info set pickupDate = '"
				+ new Timestamp(System.currentTimeMillis())
				+ "', pickupType = "+PickupConstant.openself+" where pickupType = "+PickupConstant.open+" and" + " boxId = "
				+ boxId + " and deliveryTel = '" + tel + "' and cabinetId = '"
				+ cabinetId + "' ");
	}

	public List<Map<String, Object>> queryDeliving(String tel,
			List<Integer> pickupType,String page) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ri.boxId,ri.expressNumber,ri.receiveTel,ri.deliveryTel,ui.username,ui.company,ri.deliveryDate ,ri.pickupDate ,CONCAT(bi.cabinetName,bc.cabinetNumber,'格') as boxaddress FROM "
				+ " record_info ri,box_cabinet bc,box_info bi,user_info ui where ri.cabinetId = bc.cabinetId "
				+ "and ri.boxId = bc.cabinetNumber and ri.cabinetId = bi.cabinetId and ui.tel = ri.deliveryTel"
				+ " and ri.deliveryTel = '"+tel+"' and ri.pickupType in ");
		    sb.append("("+StringUtils.join(pickupType, ",")+")");
			sb.append(" order by  ri.deliveryDate  asc");
			
			if(StringUtils.isNotBlank(page)){
				sb.append(PageUtils.getPageSQL(Integer.parseInt(page)));
			}
			
		   return jdbcTemplate.queryForList(sb.toString());

	}

	/**
	 * 获取柜子对于的地址
	 * 
	 * @param cabinetId
	 * @param boxId
	 * @return
	 */
	public List<Map<String,Object>> getAddress(String cabinetId, Integer boxId) {

		return jdbcTemplate
				.queryForList("select bi.communityName,bc.cabinetNumber,bi.cabinetName from  box_cabinet bc,box_info bi "
						+ "where bc.cabinetId = bi.cabinetId and bi.cabinetId = ? and bc.cabinetNumber = ?",
						new Object[] { cabinetId, boxId });
	}


	/**
	 * 快递员打开柜子
	 * @param tel
	 * @param cabinetId
	 * @param boxId
	 * @param days
	 */
	public void deliverypickup(String tel, String cabinetId, Integer boxId,
			Integer days) {
		jdbcTemplate.update("update record_info set dateCount = ? ,pickupType = ?,pickupDate = ? where" +
				" boxId = ? and cabinetId = ? and deliveryTel = ? and pickupType = ?",days,PickupConstant.deliveryopen,new Timestamp(System.currentTimeMillis())
		,boxId,cabinetId,tel,PickupConstant.confirm);
		jdbcTemplate.update("update box_cabinet set  cabinetIsBlank = 0 where" +
				" cabinetNumber = ?  and  cabinetId = ?",boxId,cabinetId);
	}

//	public void refreshBox() {
//		String sql = "UPDATE box_cabinet bc left join record_info ri on ri.cabinetId = bc.cabinetId "
//				+ " SET bc.cabinetIsBlank = 0 where bc.cabinetNumber IN (SELECT	record.boxId FROM"
//				+ "	record_info record WHERE record.deliveryIsConfirm = 0 or record.pickupType = 1 "
//				+ "AND record.deliveryDate < DATE_SUB(now(), INTERVAL 10 MINUTE)) ";
//		jdbcTemplate.execute(sql);
//	}

	public void deleteCancelBox() {
		String sql = "delete from record_info where pickupType = "
				+ PickupConstant.cancel;
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 得到该用户延期的天数和用于判断用户有没扫描错柜子
	 * @param tel
	 * @param cabinetId
	 * @param boxId
	 * @return
	 */
	public Integer getOverDayCount(String tel,String cabinetId,Integer boxId){
		try {
			 return jdbcTemplate.queryForInt("select IFNULL(datediff(now(),deliveryDate),0) from record_info " +
						" where cabinetId = ? and boxId = ? and deliveryTel = ? and pickupType = "+PickupConstant.confirm,cabinetId,boxId,tel);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
