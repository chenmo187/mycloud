package com.mycloud.pojo;

import java.io.Serializable;

/**
 * 作者 clarechen
 * 时间 2015/12/11
 */
public class BaseResult implements Serializable{
    public String   status;
    public String   msg;         //错误码 0没错误其他见附件错误码
    public int      code;        //状态码0 失败1 成功
}
