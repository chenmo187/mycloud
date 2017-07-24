package com.mycloud.service;

import com.mycloud.pojo.User;

public interface UserService {

	User login(User user) throws Exception;

	User register(User user) throws Exception;

}
