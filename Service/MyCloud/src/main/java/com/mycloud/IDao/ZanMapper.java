package com.mycloud.IDao;

import java.util.Map;

import com.mycloud.pojo.Zan;

public interface ZanMapper {
	Zan checkZanNote(Map<String, String> map) throws Exception;

	void ZanNote(Map<String, String> map) throws Exception;
}
