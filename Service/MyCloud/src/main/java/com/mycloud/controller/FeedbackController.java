package com.mycloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycloud.pojo.Feedback;
import com.mycloud.result.BaseResult;
import com.mycloud.result.ResultUtils;
import com.mycloud.service.FeedbackService;
import com.mycloud.utils.ParameterUtils;

@Controller
@RequestMapping("feedback")
public class FeedbackController {
	@Autowired
	private FeedbackService feedbackService;

	@RequestMapping("submitfeedback")
	@ResponseBody
	public BaseResult createFeedback(Feedback feedback) throws Exception {

		ParameterUtils.checkParameter(feedback.getUserid(), feedback.getContent());

		feedbackService.createFeedback(feedback);
		return ResultUtils.getResultByData(null);

	}

}
