package com.ustc.box.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ustc.box.dao.PageBean;
import com.ustc.box.entity.UserInfo;
import com.ustc.box.service.RecordService;
import com.ustc.box.service.UserService;



@Controller
@RequestMapping("/record")
public class RecordController {

	@Autowired
	private RecordService recordService;
	
	@RequestMapping(value = "/recordManage.do")
	public String index() {
		return "boxrecord/recordManage";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/recordManageList.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object>  userlist(int sEcho, int iColumns,
			int iDisplayStart, int iDisplayLength,String userName,HttpServletRequest request) {
		// 用来存储返回结果的Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 用来存放列表数据的双层List
		List<List<Object>> aaData = new ArrayList<List<Object>>();
		String sSortDir_0 = request.getParameter("sSortDir_0");
		String sColumn = StringUtils.isNotEmpty(request
				.getParameter("iSortCol_0")) ? request.getParameter("sColumns")
				.split(",")[Integer.valueOf(request.getParameter("iSortCol_0"))]
				: null;
		PageBean<Object[]> records = recordService.page(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,request);
		long i = 0;
		for (Object[] data : records.getData()) {
			if (i >= records.getCount()) {
				break;
			}
			List<Object> list = new ArrayList<Object>();
			list.add(data[0]);
			list.add(data[1]);
			list.add(data[2]);
			list.add(data[3]);
			list.add(data[4]);
			list.add(data[5]);
			list.add(data[6]);
			list.add(data[7]);
			list.add(data[8]);
			list.add(data[9]);
			list.add(getDeliveryState(data[10].toString()));
			list.add(data[11]);
			list.add(data[12]);
			
			aaData.add(list);
			i++;
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/sendMsg.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public boolean sendMsg(String recordId){
		return recordService.sendRecordMsg(recordId);
		
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteRecordInfoById.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public boolean deleteRecordInfoById(String id){
		 recordService.deleteRecordInfoById(id);
		 return true;
		
	}
	
	
	public String getDeliveryState(String state){
		
		switch (Integer.parseInt(state)) {
		case 0:
			return "用户未确认";
		case 1:
			return "正在派件";
		case 2:
			return "用户已取件";
		case 3:
			return "快递员取消返回";
		case 4:
			return "快递员重复打开柜子";
		case 5:
			return "快递员自取";
		case 6:
			return "帮他代取";
		default:
			return null;
		}
		
	}


}
