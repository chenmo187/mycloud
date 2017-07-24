package com.mycloud.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mycloud.constant.Constant;
import com.mycloud.redis.RedisClientTemplate;
import com.mycloud.result.ResultUtils;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception {
		
		String urls[] = new String[]{"login","register","html","jsp"};
		String url = request.getRequestURL().toString();
		
		if(url.endsWith("mycloud/")){
			return true;
		}
		
		Boolean flag = false;
		for (String u : urls) {
			if(url.contains(u)){
			  return true;
			}
		}
		
		if(!flag){
			
			String token = request.getHeader("Cookie");
			String userId =  request.getParameter("userid");
			if(token == null || userId == null){
				flag = false;
			}else {
            
				if(token.equals(redisClientTemplate.get(Constant.TOKEN + userId))){
					redisClientTemplate.expire(Constant.TOKEN + userId, Constant.EXPIRE_TIME);
					flag = true;
				}else {
					flag = false;
				}
				
			}
			
		}
		if(!flag){

			throw  ResultUtils.getExceptionByCode(ResultUtils.SESSION_EXPIRED);
		}
		return flag;
		
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
			throws Exception {
	}

	/**
	 * This implementation is empty.
	 */
	@Override
	public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	
}
