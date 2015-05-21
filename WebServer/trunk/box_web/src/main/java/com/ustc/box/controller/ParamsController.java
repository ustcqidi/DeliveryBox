package com.ustc.box.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ustc.box.service.ParamsService;



@Controller
@RequestMapping("/params")
public class ParamsController {

	@Autowired
	private ParamsService paramsService;
	
	@RequestMapping(value = "/list.json")
	public String index() {
		return "params/paramslist";
	}
	
	



}
