package com.mycloud.net;


import com.mycloud.pojo.BaseResult;
import com.mycloud.utils.ToastUtils;

/**
 * Created by clarechen on 2016/2/2.
 */
public abstract class HttpListener<T extends BaseResult> {

    abstract public void onReallySuccess(T t);

    public void onFailure(String error) {
        if (NetworkUtils.isNetworkAvailable()) {
            ToastUtils.showToast("服务器繁忙");
        } else {
            ToastUtils.showToast("请检查网络");
        }
    }

    public void onCodeError(int code, String msg) {
        ToastUtils.showToast(msg);
    }

    public void onFinish() {
    }

}
