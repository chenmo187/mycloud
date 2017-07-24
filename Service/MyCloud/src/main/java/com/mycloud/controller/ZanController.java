package com.mycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycloud.result.BaseResult;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.ZanService;
import com.mycloud.utils.ParameterUtils;

@Controller
@RequestMapping("zan")
public class ZanController {
	
	@Autowired
	private ZanService zanService;

	@RequestMapping("zan")
	@ResponseBody
	public BaseResult zanNote(String userid, String noteid) throws Exception {
		
		ParameterUtils.checkParameter(userid,noteid);
		
		zanService.zanNote(userid, noteid);
		return ResultUtils.getResultByData(null);
	}
}
