package com.mycloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycloud.IDao.FeedbackMapper;
import com.mycloud.pojo.Feedback;
import com.mycloud.service.FeedbackService;

@Service
public class FeedbackServiceImpl implements FeedbackService {
	@Autowired
	private FeedbackMapper feedbackMapper;

	@Override
	@Transactional
	public void createFeedback(Feedback feedback) throws Exception {
		feedbackMapper.createFeedback(feedback);
	}

}
