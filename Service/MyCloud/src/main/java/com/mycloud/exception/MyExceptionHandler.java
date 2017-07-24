package com.mycloud.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.mycloud.result.BaseResult;
import com.mycloud.result.ResultUtils;

public class MyExceptionHandler implements HandlerExceptionResolver {
	@ResponseBody
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		BaseResult baseResult = new BaseResult();
		if (ex instanceof BaseException) {
			BaseException exception = (BaseException) ex;
			baseResult.setCode(exception.getCode());
			baseResult.setMsg(exception.getMsg());
		} else {
			baseResult = ResultUtils.setResultByCode(ResultUtils.SERVICE_ERROR_CODE, baseResult);
		}
		try {
			response.setHeader("Content-type", "text/json;charset=UTF-8");
			response.getWriter().write(JSON.toJSONString(baseResult));
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
