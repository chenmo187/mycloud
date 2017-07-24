package com.mycloud.utils;

import com.mycloud.result.ResultUtils;

public class ParameterUtils {

	public static void checkParameter(Object... parameter) throws Exception {
		for (Object param : parameter) {
			if (org.springframework.util.StringUtils.isEmpty(param)) {
				throw ResultUtils.getExceptionByCode(ResultUtils.REQUEST_PARAMETERS_CODE);
			}
		}
	}
}
