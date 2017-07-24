package com.mycloud.IDao;

import org.springframework.stereotype.Repository;

import com.mycloud.pojo.User;

@Repository
public interface UserMapper {

	User getUser(User user);

	void register(User user);

	User checkUser(User user);
}