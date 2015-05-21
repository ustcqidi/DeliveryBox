package com.ustc.box.controller;
import java.util.ArrayList;
import java.util.Date;
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
import com.ustc.box.entity.AppUser;
import com.ustc.box.entity.BoxInfo;
import com.ustc.box.entity.ChargeRecord;
import com.ustc.box.entity.UserInfo;
import com.ustc.box.service.AccountService;
import com.ustc.box.service.BoxInfoService;
import com.ustc.box.service.DeliveryService;



@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private DeliveryService deliveryService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private BoxInfoService boxInfoService;
	
	
	@RequestMapping(value = "/charge.do")
	public String charge(String tel,Model model) {
		if(StringUtils.isNotEmpty(tel))
		model.addAttribute("tel", tel);
		model.addAttribute("ptel", tel);
		return "account/charge";
	}
	
	/**
	 * 充值记录
	 * @return
	 */
	@RequestMapping(value = "/chargeRecord.do")
	public String chargeRecord() {
		return "account/chargelist";
	}
	
	
	/**
	 * 收入记录
	 * @return
	 */
	@RequestMapping(value = "/payRecord.do")
	public String payRecord() {
		return "account/paylist";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/chargelist.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object>  chargelist(int sEcho, int iColumns,
			int iDisplayStart, int iDisplayLength,String tel,String username,String charge_type,HttpServletRequest request) {
		// 用来存储返回结果的Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 用来存放列表数据的双层List
		List<List<Object>> aaData = new ArrayList<List<Object>>();
		String sSortDir_0 = request.getParameter("sSortDir_0");
		String sColumn = StringUtils.isNotEmpty(request
				.getParameter("iSortCol_0")) ? request.getParameter("sColumns")
				.split(",")[Integer.valueOf(request.getParameter("iSortCol_0"))]
				: null;
		PageBean<Object[]> records = accountService.page(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,username,tel,charge_type);
		long i = 0;
		for (Object[] o : records.getData()) {
			List<Object> list = new ArrayList<Object>();
			list.add(++i);
			list.add(o[1]);
			list.add(o[2]);
			list.add(o[3]);
			list.add(o[4]);
			list.add(o[5]);
			list.add(o[6]);
			aaData.add(list);
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/payDetailList.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object>  payDetailList(int sEcho, int iColumns,
			int iDisplayStart, int iDisplayLength,String cabinetId,String startTime,String endTime,HttpServletRequest request) {
		// 用来存储返回结果的Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 用来存放列表数据的双层List
		List<List<Object>> aaData = new ArrayList<List<Object>>();
		String sSortDir_0 = request.getParameter("sSortDir_0");
		String sColumn = StringUtils.isNotEmpty(request
				.getParameter("iSortCol_0")) ? request.getParameter("sColumns")
				.split(",")[Integer.valueOf(request.getParameter("iSortCol_0"))]
				: null;
		PageBean<Object[]> records = accountService.payDetails(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,cabinetId,startTime,endTime);
		for (Object[] o : records.getData()) {
			List<Object> list = new ArrayList<Object>();
			list.add(o[0]);
			list.add(o[2]);
			list.add(o[3]);
			list.add(o[1]);
			list.add(o[4]);
			aaData.add(list);
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping(value = "/payTotalList.json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public Map<String, Object>  payTotalList(int sEcho, int iColumns,
			int iDisplayStart, int iDisplayLength,String cabinetId,String startTime,String endTime,HttpServletRequest request) {
		// 用来存储返回结果的Map
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 用来存放列表数据的双层List
		List<List<Object>> aaData = new ArrayList<List<Object>>();
		String sSortDir_0 = request.getParameter("sSortDir_0");
		String sColumn = StringUtils.isNotEmpty(request
				.getParameter("iSortCol_0")) ? request.getParameter("sColumns")
				.split(",")[Integer.valueOf(request.getParameter("iSortCol_0"))]
				: null;
		PageBean<Object[]> records = accountService.paypage(iDisplayStart,
				iDisplayLength, sSortDir_0, sColumn,cabinetId,startTime,endTime);
		long i = 0 ;
		for (Object[] o : records.getData()) {
			if (i >= records.getCount()) {
				break;
			}
			List<Object> list = new ArrayList<Object>();
			BoxInfo boxInfo = boxInfoService.getBoxInfoByCabinetId((o[0].toString()));
			list.add(o[0]);
			list.add(boxInfo.getSmallCount());
			list.add(o[1]);
			list.add(o[2]);
			aaData.add(list);
		}
		resultMap.put("sEcho", sEcho + 1);
		resultMap.put("iTotalRecords", records.getCount());
		resultMap.put("iTotalDisplayRecords", records.getCount());
		resultMap.put("aaData", aaData);
		return resultMap;
	}

	@RequestMapping(value = "/saveCharge.json")
	public String  saveOrUpdate(HttpServletRequest request,Model model,String tel,String ptel,String money) {
		if(StringUtils.isEmpty(ptel)||StringUtils.isEmpty(tel)){
			model.addAttribute("msg", "输入号码不能为空");
			model.addAttribute("tel", tel);
			return "account/charge";
		}
		
		if(!ptel.equals(tel)){
			model.addAttribute("msg", "两次号码输入不一致");
			model.addAttribute("tel", tel);
			return "account/charge";
		}
		
		if(StringUtils.isEmpty(money)){
			model.addAttribute("msg", "输入金额不能为空");
			model.addAttribute("tel", tel);
			return "account/charge";
		}
		
		if(StringUtils.isNotEmpty(money)&&!isDouble(money)){
			model.addAttribute("msg", "请输入正确金额");
			model.addAttribute("tel", tel);
			return "account/charge";
		}
		
		
		AppUser appUser = deliveryService.findUserByTel(tel);
		if(appUser == null){
			model.addAttribute("msg", "该用户不存在");
			model.addAttribute("tel", tel);
			return "account/charge";
		}
		
		Double newbalance = appUser.getBalance()+Double.valueOf(money);
		appUser.setBalance(newbalance);
		deliveryService.update(appUser);
		ChargeRecord cr = new ChargeRecord();
		cr.setAddDate(new Date());
		cr.setMoney(Double.valueOf(money));
		cr.setTel(tel);
		cr.setCharge_type(0);
		accountService.saveChargeRecord(cr);
		return "redirect:/delivery/list.json";
		
	}

//	
//	
//	@ResponseBody
//	@RequestMapping(value = "/getUserAppById.json")
//	public AppUser getUserAppById(String id) {
//		return deliveryService.getAppUserById(Integer.parseInt(id));
//	}
//	
//	
//	@ResponseBody
//	@RequestMapping(value = "/deleteUserById.json")
//	public String deleteUserById(String id) {
//		deliveryService.deleteUserById(id);
//		return "1";
//		
//	}
	
	public boolean isDouble(String str)
	{
	   try
	   {
	      Double.parseDouble(str);
	      return true;
	   }
	   catch(NumberFormatException ex){}
	   return false;
	}

}
