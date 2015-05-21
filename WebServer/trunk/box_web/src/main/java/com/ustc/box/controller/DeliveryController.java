package com.ustc.box.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ustc.box.dao.PageBean;
import com.ustc.box.entity.AppUser;
import com.ustc.box.entity.UserInfo;
import com.ustc.box.service.DeliveryService;
import com.ustc.box.service.UserService;
import com.ustc.box.utils.RequestUtils;



@Controller
@RequestMapping("/delivery")
public class DeliveryController {

	@Autowired
	private DeliveryService deliveryService;
	
	@RequestMapping(value = "/list.json")
	public String index() {
		return "delivery/deliverylist";
	}
	
	
	@RequestMapping(value = "/updatePassWd.json")
	public String updatePassWd() {
		return "auth/password";
	}
	
	    

	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate.json")
	public String  saveOrUpdate(HttpServletRequest request,String json) {
		 AppUser appUser = new Gson().fromJson(json, AppUser.class);
		 deliveryService.saveOrUpdate(appUser);
		 return "1";
	}

	
	
	@ResponseBody
	@RequestMapping(value = "/getUserAppById.json")
	public AppUser getUserAppById(String id) {
		return deliveryService.getAppUserById(Integer.parseInt(id));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteUserById.json")
	public String deleteUserById(String id) {
		deliveryService.deleteUserById(id);
		return "1";
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deliverylist.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object>  userlist(int sEcho, int iColumns,
			int iDisplayStart, int iDisplayLength,String username,String company,String tel, HttpServletRequest request) {
		// 用来存储返回结果的Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 用来存放列表数据的双层List
		List<List<Object>> aaData = new ArrayList<List<Object>>();
		String sSortDir_0 = request.getParameter("sSortDir_0");
		String sColumn = StringUtils.isNotEmpty(request
				.getParameter("iSortCol_0")) ? request.getParameter("sColumns")
				.split(",")[Integer.valueOf(request.getParameter("iSortCol_0"))]
				: null;
		PageBean<AppUser> records = deliveryService.page(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,username,company,tel);
		long i = 0;
		for (AppUser user : records.getData()) {
			if (i >= records.getCount()) {
				break;
			}
			List<Object> list = new ArrayList<Object>();
			list.add(user.getUsername());
			list.add(user.getCompany());
			list.add(user.getTel());
			list.add(user.getBalance());
			list.add(user.getDelivery_price_desc());
			list.add(user.getUser_desc());
			list.add(user.getId());
			aaData.add(list);
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}
	


}
