package com.mycloud.net;

import com.mycloud.MyCloudApplication;
import com.mycloud.memory.Memory;
import com.mycloud.pojo.BaseResult;
import com.mycloud.ui.BaseActivity;
import com.mycloud.utils.LogTool;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by clarechen on 2016/2/1.
 */
public class HttpClient {


    private static HttpVolley httpVolley = HttpVolley.getInstance();
    private static HttpClient httpClient;


    private HttpClient() {
    }

    public static HttpClient getInstance() {
        if (httpClient == null) {
            synchronized (HttpClient.class) {
                if (httpClient == null) {
                    httpClient = new HttpClient();
                }
            }
        }
        return httpClient;
    }


    public String getIntroductionUrl() {
        return URLConfig.BASEURL_APP + URLConfig.APPINTRODUCTION;
    }

    public <T extends BaseResult> void post(final String url, final Class<T> tClass,  Map<String, String> params, final HttpListener<T> httpListener) {
        if (Memory.getUser() != null) {
            if(params==null){
               params = new HashMap<>();
            }
            params.put("userid", Memory.getUser().data.id);
        }
        httpVolley.request(URLConfig.BASEURL_APP + url, tClass, params, httpListener);
        LogTool.d("request:   " + URLConfig.BASEURL_APP + url + "   params" + params.toString());
    }

    public <T extends BaseResult> void post(final BaseActivity baseActivity, final String url, final Class<T> tClass,  Map<String, String> params, final HttpListener<T> httpListener) {
        if (Memory.getUser() != null) {
            if(params==null){
                params = new HashMap<>();
            }
            params.put("userid", Memory.getUser().data.id);
        }
        httpVolley.request(baseActivity, URLConfig.BASEURL_APP + url, tClass, params, httpListener);
        LogTool.d("request:   " + URLConfig.BASEURL_APP + url + "   params" + params.toString());
    }

    public <T extends BaseResult> void postFile(String url,final Class<T> tClass,  Map<String, String> params, Map<String, String> fileParams, final HttpListener<T> httpListener) {
        if (Memory.getUser() != null) {
            if(params==null){
                params = new HashMap<>();
            }
            params.put("userid", Memory.getUser().data.id);
        }
        httpVolley.postFile(URLConfig.BASEURL_APP + url, tClass, params, fileParams, httpListener);
        LogTool.d("request:   " + URLConfig.BASEURL_APP + url + "   params" + params.toString());
    }

    public <T extends BaseResult> void postFile(final BaseActivity baseActivity,final String url,final Class<T> tClass,  Map<String, String> params, Map<String, String> fileParams, final HttpListener<T> httpListener) {
        if (Memory.getUser() != null) {
            params.put("userid", Memory.getUser().data.id);
        }
        httpVolley.postFile(baseActivity, URLConfig.BASEURL_APP + url, tClass, params, fileParams, httpListener);
        LogTool.d("request:   " + URLConfig.BASEURL_APP + url + "   params" + params.toString());
    }

}
