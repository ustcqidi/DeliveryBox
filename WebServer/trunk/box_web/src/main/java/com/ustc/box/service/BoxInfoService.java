package com.ustc.box.service;

import java.util.HashMap;
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
import com.ustc.box.entity.BoxCabinet;
import com.ustc.box.entity.BoxInfo;
import com.ustc.box.utils.ParamConstant;
import com.ustc.box.utils.PropertiesUtils;
import com.ustc.box.utils.RequestUtils;
import com.ustc.box.utils.TwoDimensionCode;

@Service
@Transactional
public class BoxInfoService {

	@Resource
	private HDao dao;

	private static String boxImgpath = PropertiesUtils
			.getProperty("boxImg_path");
	private static String downloadImg = PropertiesUtils
			.getProperty("downloadImg_path");

	public PageBean<Object[]> page(int iDisplayStart, int iDisplayLength,
			String sSortDir_0, String sColumn, HttpServletRequest request) {
		Map<String, String> params = RequestUtils.parameterToMap(request);
		
		// '#province,#city,#region,#communityName,#cabinetId,#communityName',
		PageBean<Object[]> pageBean = new PageBean<Object[]>();
		pageBean.setStart(iDisplayStart);
		pageBean.setLimit(iDisplayLength);
		String sql = "select cabinetId,cabinetName,communityName,province,city,region,communityAddress,"
				+ "communityContact,manager,update_time,smallCount from box_info where 1 =1  ";
		if (StringUtils.isNotEmpty(params.get("province"))) {
			sql += " and province like '%" + params.get("province") + "%'";
		}
		if (StringUtils.isNotEmpty(params.get("city"))) {
			sql += " and city like '%" + params.get("city") + "%'";
		}
		if (StringUtils.isNotEmpty(params.get("region"))) {
			sql += " and region like '%" + params.get("region") + "%'";
		}
		if (StringUtils.isNotEmpty(params.get("communityName"))) {
			sql += " and communityName like '%" + params.get("communityName")
					+ "%'";
		}
		if (StringUtils.isNotEmpty(params.get("cabinetId"))) {
			sql += " and cabinetId like '%" + params.get("cabinetId") + "%'";
		}
		if (StringUtils.isNotEmpty(params.get("communityAddress"))) {
			sql += " and communityAddress like '%"
					+ params.get("communityAddress") + "%'";
		}

		if (!StringUtils.isEmpty(sSortDir_0) && !StringUtils.isEmpty(sColumn)) {
			sql += " Order by " + sColumn + " " + sSortDir_0;
		}
		return dao.findBySQLPage(pageBean, sql.toString());
	}

	public BoxInfo getBoxInfoById(String cabinetId) {
		return dao.findUniqueResult("from BoxInfo where cabinetId = ?", cabinetId);
	}

	public BoxInfo produceImg(BoxInfo boxInfo) {
		Map<String, Map<String, String>> json = new HashMap<String, Map<String, String>>();
		Map<String, String> info = new HashMap<String, String>();
		info.put("id", boxInfo.getCabinetId());
		info.put("address", boxInfo.getCabinetName());
		json.put("cabinet_info", info);
		String content = new Gson().toJson(json);
		TwoDimensionCode hander = new TwoDimensionCode();
		hander.encoderQRCode(content, boxImgpath + boxInfo.getCabinetId()
				+ ".png", "png", 10);
		boxInfo.setImg_url(downloadImg + boxInfo.getCabinetId() + ".png");
		return boxInfo;
	}

	public Object[] getRecordInfo(String cabinetId, String cabinetNumber) {
		return dao
				.findBySQLUniqueResult(
						"select r.deliveryDate,r.receiveTel,r.deliveryTel from  record_info r where  "
								+ " (r.pickupType = 1 or  r.pickupType = 0) and r.cabinetId = ? and r.boxId = ?",
						cabinetId, cabinetNumber);
	}

	public List<Object[]> getBoxOpenInfo(String id) {
		return dao
				.findBySQL(
						"select b.id,b.cabinetNumber,b.cabinetType,b.cabinetIsBlank,b.type,'1','2',b.cabinetId from box_cabinet b where b.cabinetId = ? order by b.cabinetNumber asc",
						id);
	}

	public void updateBoxInfo(BoxInfo boxInfo) {
		Number smallCount = dao.findBySQLUniqueResult(
				"select smallCount from box_info where cabinetId = ? ",
				boxInfo.getCabinetId());
		if(smallCount == null){
			smallCount = 0;
		}
		if (smallCount.intValue() != boxInfo.getSmallCount()) {
			// 限制5个以上的柜子更新
			if (boxInfo.getSmallCount() > 5) {
				return;
			}

			// 增加柜子
			if (boxInfo.getSmallCount() > smallCount.intValue()) {
				Number maxBoxMumber = dao
						.findBySQLUniqueResult(
								"select max(cabinetNumber) from box_cabinet where cabinetId = ? ",
								boxInfo.getCabinetId());
				addSmallBox(maxBoxMumber.intValue(),
						boxInfo.getSmallCount() - smallCount.intValue(),
						boxInfo);
			} else {
				// 柜子减少
				// 得到柜子减少后面的序号
				Integer boxNum = 20*(1+boxInfo.getSmallCount());
				// 判断
				dao.bulkUpdateBySQL(
						"delete from box_cabinet where cabinetId = ? and cabinetNumber > ? ",
						boxInfo.getCabinetId(), boxNum);
			}
		} 
		
		dao.update(boxInfo);

	}

	/**
	 * 增加副柜子
	 * 
	 * @param maxBoxMumber
	 * @param addcount
	 *            //增加的柜子数量
	 */
	public void addSmallBox(int maxBoxMumber, Integer addcount, BoxInfo boxInfo) {
		// 以下是处理副柜子的情况
		// 大柜前两个
		int count = maxBoxMumber + 1;// 从第二个开始计数
		for (int j = 0; j < addcount; j++) {

			// 副柜大柜子
			for (int i = 1; i <= ParamConstant.BDCabinetSize; i++) {
				saveBoxCabinet(boxInfo, count, ParamConstant.DCabinetType);
				count++;
			}

			// 副柜子小柜
			for (int i = 1; i <= ParamConstant.BXCabinetSize; i++) {
				saveBoxCabinet(boxInfo, count, ParamConstant.XCabinetType);
				count++;
			}

			for (int i = 1; i <= ParamConstant.BZCabinetSize; i++) {
				saveBoxCabinet(boxInfo, count, ParamConstant.ZCabinetType);
				count++;
			}
		}
	}

	/**
	 * 保存副柜
	 * 
	 * @param boxInfo
	 * @param cabinetNumber
	 * @param CabinetType
	 */
	public void saveBoxCabinet(BoxInfo boxInfo, Integer cabinetNumber,
			Integer CabinetType) {
		BoxCabinet bc = new BoxCabinet();
		bc.setBoxInfo(boxInfo);
		bc.setCabinetNumber(cabinetNumber);
		bc.setCabinetType(CabinetType);
		bc.setCabinetIsBlank(0);
		bc.setType(2);
		dao.save(bc);
	}
	
	
	public BoxInfo getBoxInfoByCabinetId(String cabinetId){
		return dao.findUniqueResult("from BoxInfo where cabinetId = ?", cabinetId);
	}
}
