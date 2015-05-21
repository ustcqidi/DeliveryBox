package com.ustc.box.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ustc.box.dao.HDao;
import com.ustc.box.dao.PageBean;
import com.ustc.box.entity.AppUser;
import com.ustc.box.entity.ChargeRecord;

@Service
@Transactional
public class AccountService {

	@Resource
	private HDao dao;




	
	public void saveChargeRecord(ChargeRecord chargeRecord){
	    dao.save(chargeRecord);
	}
	
	public PageBean<Object[]> page(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, String username,String tel,String charge_type) {
		PageBean<Object[]> pageBean = new PageBean<Object[]>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		String sql = "select c.id,c.addDate,c.tel,c.money,u.company,u.username,c.charge_type from charge_record c left join user_info u on u.tel = " +
				" c.tel where 1  = 1 ";
		if(StringUtils.isNotEmpty(username)){
			sql+=" and u.username like '%"+username+"%' ";
		}
		if(StringUtils.isNotEmpty(tel)){
			sql+=" and c.tel like '%"+tel+"%' ";
		}
		if(StringUtils.isNotEmpty(charge_type)){
			sql+=" and  charge_type ="+charge_type;
		}
		
		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by " + sColumn + " " + sSortDir_0;
		}
		return dao.findBySQLPage(pageBean, sql.toString());
	}
	
	public PageBean<Object[]> paypage(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, String cabinetId,String startTime,String endTime) {
		PageBean<Object[]> pageBean = new PageBean<Object[]>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		String sql = "select r.cabinetId,count(r.cabinetId),SUM(c.money) from consume_record c" +
				" inner join record_info r on r.id = c.record_id  where c.status = 0 ";
		List<String> params = new ArrayList<String>();
		if (StringUtils.isNotEmpty(cabinetId)) {
			sql += " and r.cabinetId like '%" + cabinetId + "%' ";
		}
		if(StringUtils.isNotEmpty(startTime)){
			sql+=" and c.addDate >= ? ";
			params.add(startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			sql+=" and c.addDate <= ? ";
			params.add(endTime);
		}
		sql += " GROUP BY  " +"r.cabinetId ";
		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by " + sColumn + " " + sSortDir_0;
		}
		return dao.findBySQLPage(pageBean, sql.toString(),params.toArray());
	}
	
	
	public PageBean<Object[]> payDetails(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, String cabinetId,String startTime,String endTime) {
		PageBean<Object[]> pageBean = new PageBean<Object[]>();
		List<String> params = new ArrayList<String>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		String sql = "select r.cabinetId,c.money,c.addDate,c.tel,r.boxId from consume_record c " +
				"inner join record_info r on r.id = c.record_id   where c.status = 0 ";
		if (StringUtils.isNotEmpty(cabinetId)) {
			sql += " and r.cabinetId like '%" + cabinetId + "%' ";
		}
		if(StringUtils.isNotEmpty(startTime)){
			sql+=" and c.addDate >= ? ";
			params.add(startTime);
		}
		if(StringUtils.isNotEmpty(endTime)){
			sql+=" and c.addDate <= ? ";
			params.add(endTime);
		}
		
		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by " + sColumn + " " + sSortDir_0;
		}
		return dao.findBySQLPage(pageBean, sql.toString(),params.toArray());
	}

}
