package com.mycloud.result;

import java.util.HashMap;

import com.mycloud.exception.BaseException;
import com.mycloud.result.BaseResult;

public class ResultUtils {
	public final static Integer SUCCESS_CODE = 200;
	public final static String SUCCESS_MSG = "成功";

	public final static Integer PWD_ERROR_CODE = 300;
	public final static String PWD_ERROR_MSG = "账号或密码不正确";

	public final static Integer USER_NOTTFIND_CODE = 301;
	public final static String USER_NOTTFIND_MSG = "用户不存在";

	public final static Integer SERVICE_ERROR_CODE = 302;
	public final static String SERVICE_ERROR_MSG = "服务器繁忙";

	public final static Integer USER_EXIST_CODE = 303;
	public final static String USER_EXIST_MSG = "用户已存在";

	public final static Integer REQUEST_PARAMETERS_CODE = 304;
	public final static String REQUEST_PARAMETERS_MSG = "请求参数不正确";

	public final static Integer NOTEFOLDER_EXIST_CODE = 305;
	public final static String NOTEFOLDER_EXIST_MSG = "相同名称的文件夹已存在";

	public final static Integer FOLDER_NOFIND_CODE = 306;
	public final static String FOLDER_NOFIND_MSG = "找不到此文件夹";

	public final static Integer NOTE_EXIST_CODE = 307;
	public final static String NOTE_EXIST_MSG = "文件夹中已有该笔记";
	
	public final static Integer NOTE_HADZAN_CODE = 308;
	public final static String NOTE_HADZAN__MSG = "已经赞过了";
	
	public final static Integer SESSION_EXPIRED  = 309;
	public final static String SESSION_EXPIRED__MSG = "回话失效，请重新登录";

	private static HashMap<Integer, String> result = new HashMap<Integer, String>();

	static {
		result.put(SUCCESS_CODE, SUCCESS_MSG);
		result.put(PWD_ERROR_CODE, PWD_ERROR_MSG);
		result.put(USER_NOTTFIND_CODE, USER_NOTTFIND_MSG);
		result.put(SERVICE_ERROR_CODE, SERVICE_ERROR_MSG);
		result.put(USER_EXIST_CODE, USER_EXIST_MSG);
		result.put(REQUEST_PARAMETERS_CODE, REQUEST_PARAMETERS_MSG);
		result.put(NOTEFOLDER_EXIST_CODE, NOTEFOLDER_EXIST_MSG);
		result.put(FOLDER_NOFIND_CODE, FOLDER_NOFIND_MSG);
		result.put(NOTE_EXIST_CODE, NOTE_EXIST_MSG);
		result.put(NOTE_HADZAN_CODE, NOTE_HADZAN__MSG);
		result.put(SESSION_EXPIRED, SESSION_EXPIRED__MSG);
	}

	public static String getMsgByCode(int code) {
		return result.get(code);
	}

	public static BaseResult setResultByCode(int code, BaseResult baseResult) {
		baseResult.setCode(code);
		baseResult.setMsg(result.get(code));
		return baseResult;
	}

	public static BaseResult getResultByData(Object data) {
		BaseResult baseResult = new BaseResult();
		baseResult.setCode(ResultUtils.SUCCESS_CODE);
		baseResult.setMsg(ResultUtils.SUCCESS_MSG);
		baseResult.setData(data);
		return baseResult;
	}

	public static BaseException getExceptionByCode(int code) {
		BaseException baseException = new BaseException(code, result.get(code));
		return baseException;
	}

}
