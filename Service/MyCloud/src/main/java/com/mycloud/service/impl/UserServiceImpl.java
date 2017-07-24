package com.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycloud.IDao.UserMapper;
import com.mycloud.pojo.User;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper UserMapper;

	@Override
	@Transactional(readOnly = true)
	public User login(User user) {
		return UserMapper.getUser(user);
	}

	@Override
	@Transactional
	public User register(User user) throws Exception {
		if (UserMapper.checkUser(user) == null) {
			UserMapper.register(user);
			return user;
		} else {
			throw ResultUtils.getExceptionByCode(ResultUtils.USER_EXIST_CODE);
		}
	}

}
