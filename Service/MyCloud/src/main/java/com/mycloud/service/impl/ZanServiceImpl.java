package com.mycloud.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycloud.IDao.ZanMapper;
import com.mycloud.pojo.Zan;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.ZanService;

@Service
public class ZanServiceImpl implements ZanService {
	@Autowired
	private ZanMapper zanMapper;

	@Override
	@Transactional
	public void zanNote(String userid, String noteid) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("noteid", noteid);
		
		Zan zan = zanMapper.checkZanNote(map);
		if(zan != null){
			throw ResultUtils.getExceptionByCode(ResultUtils.NOTE_HADZAN_CODE);
		}else {
			zanMapper.ZanNote(map);
		}
		
	}

}
