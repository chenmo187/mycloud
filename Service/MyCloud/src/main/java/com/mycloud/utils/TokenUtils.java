package com.mycloud.utils;

public class TokenUtils {

	public static String createToken(String userName){
		
		return MD5.String2MD5(userName+System.currentTimeMillis());		
	
	}
}
