package com.ustc.box.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ustc.box.dao.PageBean;
import com.ustc.box.entity.BoxInfo;
import com.ustc.box.service.BoxInfoService;



@Controller
@RequestMapping("/boxInfo")
public class BoxInfoController {

	@Autowired
	private BoxInfoService boxInfoService;
	
	@RequestMapping(value = "/boxInfoManage.do")
	public String index() {
		return "boxInfo/boxInfoManage";
	}
	
	@RequestMapping(value = "/view.do")
	public String view(String operation,String tgId,Model model) {
		model.addAttribute("operation", operation);
		model.addAttribute("id", tgId);
		BoxInfo boxInfo = boxInfoService.getBoxInfoById(tgId);
		if(StringUtils.isEmpty(boxInfo.getImg_url())){
			boxInfoService.produceImg(boxInfo);
		}
		
		model.addAttribute("boxInfo", 	boxInfo);
		return "boxInfo/view";
	}
	
	@RequestMapping(value = "/updateBoxInfo.json")
	public String updateBoxInfo(@ModelAttribute BoxInfo boxInfo) {
		boxInfoService.updateBoxInfo(boxInfo);
		return "boxInfo/boxInfoManage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/getBoxOpenInfo.json")
	public List<Object[]> getBoxOpenInfo(String id){
		List<Object[]>  result =  boxInfoService.getBoxOpenInfo(id);
		for(Object[] o : result){
			if(Integer.parseInt(o[3].toString()) == 1 ){ //倍占用
				Object[] res = boxInfoService.getRecordInfo(o[7].toString(), o[1].toString());
				if(res != null){
					o[5] = res[0];
					o[6] = res[1];
				}else{
					o[5] = "";
					o[6] = "异常柜";
				}
				
			}else{
				o[5] = "";
				o[6] = "";
			}
		}
		return result;
	} 
	
	@ResponseBody
	@RequestMapping(value = "/boxManageList.json", method = { RequestMethod.GET,
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
		PageBean<Object[]> records = boxInfoService.page(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,request);
		long i = 0;
		for (Object[] data : records.getData()) {
			if (i >= records.getCount()) {
				break;
			}
			List<Object> list = new ArrayList<Object>();
			list.add(data[0]);
			list.add(data[3]);
			list.add(data[4]);
			list.add(data[5]);
			list.add(data[2]);
			list.add(data[1]);
			list.add(data[6]);
			list.add(data[7]);
			list.add(data[9]);
			list.add(data[10]);
			list.add(data[0]);
			aaData.add(list);
			i++;
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}
	
//	
//	@ResponseBody
//	@RequestMapping(value = "/sendMsg.json", method = { RequestMethod.GET,
//			RequestMethod.POST })
//	public boolean sendMsg(String recordId){
//		return recordService.sendRecordMsg(recordId);
//		
//	}
	


}
