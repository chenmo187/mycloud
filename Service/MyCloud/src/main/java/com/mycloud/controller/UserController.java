package com.mycloud.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycloud.constant.Constant;
import com.mycloud.pojo.User;
import com.mycloud.redis.RedisClientTemplate;
import com.mycloud.result.BaseResult;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.UserService;
import com.mycloud.utils.ParameterUtils;
import com.mycloud.utils.TokenUtils;

/**
 * Created by clarechen on 2016/1/29.
 */

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;
    
	@Autowired
	private RedisClientTemplate redisClientTemplate;
	
	@RequestMapping("login")
	@ResponseBody
	public BaseResult login(User user,HttpServletResponse resp) throws Exception {

		ParameterUtils.checkParameter(user.getUsername(), user.getPassword());

		User result = userService.login(user);
		if (result == null) {
			throw ResultUtils.getExceptionByCode(ResultUtils.PWD_ERROR_CODE);
		}
		
		String token = TokenUtils.createToken(result.getUsername());
		resp.setHeader("Set-Cookie", token);
		redisClientTemplate.set(Constant.TOKEN + result.getId(), token);
		redisClientTemplate.expire(Constant.TOKEN + result.getId(), Constant.EXPIRE_TIME);
		return ResultUtils.getResultByData(result);
	}

	@RequestMapping("register")
	@ResponseBody
	public BaseResult register(User user) throws Exception {	
		
		ParameterUtils.checkParameter(user.getUsername(),user.getPassword());
		
		User result = userService.register(user);
		return ResultUtils.getResultByData(result);
	}

}
