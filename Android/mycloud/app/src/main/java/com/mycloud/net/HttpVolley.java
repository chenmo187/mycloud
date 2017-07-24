package com.mycloud.net;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.mycloud.pojo.BaseResult;
import com.mycloud.ui.BaseActivity;
import com.mycloud.utils.LogTool;

import java.util.Map;


/**
 * 作者 clarechen
 * 时间 2015/11/16
 */
public class HttpVolley {


    private static HttpVolley mHttpVolly;

    private HttpVolley() {
    }

    public static HttpVolley getInstance() {
        if (mHttpVolly == null) {
            synchronized (HttpVolley.class) {
                if (mHttpVolly == null) {
                    mHttpVolly = new HttpVolley();
                }
            }
        }
        return mHttpVolly;
    }


    public <T extends BaseResult> void request(final String url, final Class<T> tClass, final Map<String, String> params, final HttpListener<T> httpListener) {
        RequestQueue mRequestQueue = VolleyRequestQueue.getInstance();
        mRequestQueue.add(new GsonRequest(Request.Method.POST, url, params, tClass,
                        new Response.Listener<T>() {
                            @Override
                            public void onResponse(T response) {
                                if (response != null) {
                                    if (response.code == 200) {
                                        httpListener.onReallySuccess(response);
                                    } else {
                                        httpListener.onCodeError(response.code, response.msg);
                                    }

                                }
                                httpListener.onFinish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                httpListener.onFailure(null);
                                httpListener.onFinish();
                            }
                        }
                )
        );
    }

    /**
     * @param baseActivity
     * @param url
     * @param tClass
     * @param params
     * @param httpListener
     * @param <T>          换BaseActivity过来就自动关loadin框
     */
    public <T extends BaseResult> void request(final BaseActivity baseActivity, final String url, final Class<T> tClass, final Map<String, String> params, final HttpListener<T> httpListener) {
        baseActivity.showWaiting();
        RequestQueue mRequestQueue = VolleyRequestQueue.getInstance();
        mRequestQueue.add(new GsonRequest(Request.Method.POST, url, params, tClass,
                        new Response.Listener<T>() {
                            @Override
                            public void onResponse(T response) {
                                if (response != null) {
                                    if (response.code == 200) {
                                        httpListener.onReallySuccess(response);
                                    } else {
                                        httpListener.onCodeError(response.code, response.msg);
                                    }

                                }
                                httpListener.onFinish();
                                baseActivity.stopWaiting();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                LogTool.d(error.toString()+"    "+error.getMessage());
                                httpListener.onFailure(null);
                                baseActivity.stopWaiting();
                                httpListener.onFinish();
                            }
                        }
                )
        );
    }


    public <T extends BaseResult> void postFile(String url, Class<T> tClass, final Map<String, String> params, Map<String, String> fileParams, final HttpListener<T> httpListener) {
        RequestQueue mRequestQueue = VolleyRequestQueue.getInstance();
        mRequestQueue.add(new PostUploadRequest<T>(url, params, fileParams, tClass,
                        new Response.Listener<T>() {
                            @Override
                            public void onResponse(T response) {
                                if (response != null) {
                                    if (response.code == 200) {
                                        httpListener.onReallySuccess(response);
                                    } else {
                                        httpListener.onCodeError(response.code, response.msg);
                                    }

                                }
                                httpListener.onFinish();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                httpListener.onFailure(null);
                                httpListener.onFinish();
                            }
                        }
                )
        );
    }

    public <T extends BaseResult> void postFile(final BaseActivity baseActivity, String url, Class<T> tClass, final Map<String, String> params, Map<String, String> fileParams, final HttpListener<T> httpListener) {
        baseActivity.showWaiting();
        RequestQueue mRequestQueue = VolleyRequestQueue.getInstance();
        mRequestQueue.add(new PostUploadRequest<T>(url, params, fileParams, tClass,
                        new Response.Listener<T>() {
                            @Override
                            public void onResponse(T response) {
                                if (response != null) {
                                    if (response.code == 200) {
                                        httpListener.onReallySuccess(response);
                                    } else {
                                        httpListener.onCodeError(response.code, response.msg);
                                    }

                                }
                                httpListener.onFinish();
                                baseActivity.stopWaiting();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                httpListener.onFailure(null);
                                baseActivity.stopWaiting();
                                httpListener.onFinish();
                            }
                        }
                )
        );
    }

}
