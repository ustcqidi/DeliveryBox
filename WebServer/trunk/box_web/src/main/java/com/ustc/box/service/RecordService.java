package com.ustc.box.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ustc.box.dao.HDao;
import com.ustc.box.dao.PageBean;
import com.ustc.box.utils.RequestUtils;
import com.ustc.box.utils.Send;

@Service
@Transactional
public class RecordService {

	@Resource
	private HDao dao;
	

	public PageBean<Object[]> page(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, HttpServletRequest request) {
		Map<String, String> params = RequestUtils.parameterToMap(request);
		PageBean<Object[]> pageBean = new PageBean<Object[]>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		List<String> param = new ArrayList<String>();
		String sql = "select r.deliveryDate,r.expressNumber,r.receiveTel,r.deliveryTel,b.communityName,r.cabinetId,r.boxId, "
				+ " b.cabinetName,u.company,r.pickupDate,r.pickupType,r.validatecode,r.id from record_info r left join box_info b on b.cabinetId = r.cabinetId"
				+ " left join user_info u on u.tel = r.deliveryTel "
				+ "  where 1 =1  ";
		if (StringUtils.isNotEmpty(params.get("expressNumber"))) {
			sql += " and r.expressNumber like '%" + params.get("expressNumber")
					+ "%'";
		}
		if (StringUtils.isNotEmpty(params.get("receiveTel"))) {
			sql += " and r.receiveTel like '%" + params.get("receiveTel")
					+ "%'";
		}
		if (StringUtils.isNotEmpty(params.get("deliveryTel"))) {
			sql += " and r.deliveryTel like '%" + params.get("deliveryTel")
					+ "%'";
		}
		if (StringUtils.isNotEmpty(params.get("communityName"))) {
			sql += " and b.communityName like '%"
					+ params.get("communityName") + "%'";
		}
		if (StringUtils.isNotEmpty(params.get("pickupType"))) {
			sql += " and r.pickupType = " + params.get("pickupType") ;
		}
		if (StringUtils.isNotEmpty(params.get("cabinetId"))) {
			sql += " and r.cabinetId like '%" + params.get("cabinetId")
					+ "%'";
		}

		if (StringUtils.isNotEmpty(params.get("dstartTime"))) {
			sql += " and r.deliveryDate >= ? ";
			param.add(params.get("dstartTime"));
		}

		if (StringUtils.isNotEmpty(params.get("dendTime"))) {
			sql += " and r.deliveryDate <= ? ";
			param.add(params.get("dendTime"));
		}

		if (StringUtils.isNotEmpty(params.get("rstartTime"))) {
			sql += " and r.pickupDate >= ?";
			param.add(params.get("rstartTime"));
		}

		if (StringUtils.isNotEmpty(params.get("rendTime"))) {
			sql += " and r.pickupDate <= ?";
			param.add(params.get("rendTime"));
		}

		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by r." + sColumn + " " + sSortDir_0;
		}
		return dao.findBySQLPage(pageBean, sql.toString(),param.toArray());
	}
	
	public void deleteRecordInfoById(String id){
		dao.bulkUpdateBySQL("delete from record_info where id = ?", id);
	} 

	public Boolean sendRecordMsg(String recordId) {
		Object[] result = dao
				.findBySQLUniqueResult(
						"select r.receivetel, r.validatecode,r.expressNumber,bi.communityName,r.boxId "
								+ " as boxaddress , ui.company from  record_info r left join box_info bi on bi.cabinetId = r.cabinetId LEFT JOIN user_info ui on ui.tel = r.deliveryTel  where "
								+ " r.id = ?", recordId);
		try {
			String json = Send.sendUserCodeByTel(result[0].toString(), result[1].toString(),
					result[2].toString(), result[3].toString(),result[4].toString(),result[5].toString());
			System.out.println(json);
			MsgResult rmg = new Gson().fromJson(json, MsgResult.class);
			if(rmg.getCode().equals("0")){
				return true;
			}else{
				return false;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
	}
	
	public class MsgResult{
		private String code;
		private String msg;
		public String getCode() {
			return code;
		}
		public String getMsg() {
			return msg;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public MsgResult(String code, String msg) {
			super();
			this.code = code;
			this.msg = msg;
		}	
		
	}

}
