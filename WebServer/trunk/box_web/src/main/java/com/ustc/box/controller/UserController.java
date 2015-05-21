package com.ustc.box.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ustc.box.dao.PageBean;
import com.ustc.box.entity.UserInfo;
import com.ustc.box.service.UserService;
import com.ustc.box.utils.RequestUtils;



@Controller
@RequestMapping("/auth")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/list.json")
	public String index() {
		return "auth/userlist";
	}
	
	
	@RequestMapping(value = "/updatePassWd.json")
	public String updatePassWd() {
		return "auth/password";
	}
	
	 @RequestMapping(value="/updatePassword.json")
	    public String saveBrandStoryScript(Model model, HttpServletRequest request) throws ParseException {
	    	Map<String,String> requestMap = RequestUtils.parameterToMap(request);
	    	String res = userService.updatePasswd(requestMap);
	    	if(res.equals("1")){
	    		return "auth/passwordAfter";
	    	}else{
	    		model.addAttribute("message", res);
	    		return "auth/password";
	    	}
	    }
	    

	@ResponseBody
	@RequestMapping(value = "/saveOrUpdate.json")
	public String  saveOrUpdate(String id,String name, String trueName,String desc) {
		return userService.saveOrUpdate(id, name,trueName, desc);
	}
	
	@ResponseBody
	@RequestMapping(value = "/resertPasswd.json")
	public String  resertPasswd(String id) {
		 userService.resertPasswd(id);
		 return "1";
	}
	
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getUserById.json")
	public UserInfo getUserById(String id) {
		return userService.getUserById(Integer.parseInt(id));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/deleteUserById.json")
	public String deleteUserById(String id) {
		UserInfo user =  userService.getUserById(Integer.parseInt(id));
		if(user.getName().equals("admin")){
			return "0";
		}else{
			userService.deleteUserById(id);
			return "1";
		}
		
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/userlist.json", method = { RequestMethod.GET,
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
		PageBean<UserInfo> records = userService.page(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,userName);
		long i = 0;
		for (UserInfo user : records.getData()) {
			if (i >= records.getCount()) {
				break;
			}
			List<Object> list = new ArrayList<Object>();
			list.add(user.getId());
			list.add(user.getName());
			list.add(user.getTrueName());
			list.add(user.getDes());
			list.add(user.getId());
			list.add(user.getId());
			list.add(user.getId());
			list.add(user.getId());
			aaData.add(list);
			i++;
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}
	


}
