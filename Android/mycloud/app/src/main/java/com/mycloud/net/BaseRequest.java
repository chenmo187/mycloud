package com.mycloud.net;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mycloud.memory.Memory;
import com.mycloud.utils.LogTool;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


/**
 * 作者 clarechen
 * 时间 2015/11/24
 */
public class BaseRequest<T> extends Request<T> {

    private Class<T> tClass;
    private Map<String, String> params;
    private final Response.Listener<T> mListener;

    public BaseRequest(int method, String url, Map<String, String> params, Class<T> tClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.tClass = tClass;
        this.params = params;
        this.mListener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }


    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {

        if (Memory.getToken() != null) {
            HashMap<String, String> headers = new HashMap<>();
            headers.put("Cookie", Memory.getToken());
            return headers;
        }

        return super.getHeaders();
    }

    @Override
    public Request<?> setRetryPolicy(RetryPolicy retryPolicy) {
        retryPolicy = new DefaultRetryPolicy(10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return super.setRetryPolicy(retryPolicy);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            if (response.headers != null && response.headers.get("Set-Cookie") != null) {
//                LogTool.d("responseHeaders: " + response.headers.toString());
                Memory.setToken(response.headers.get("Set-Cookie"));
            }

            String json = new String(response.data, "UTF-8");
            LogTool.d("返回的json" + json);
            return Response.success(new Gson().fromJson(json, tClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            LogTool.d("解析Encoding错误");
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            LogTool.d("Gson解析错误");
            return Response.error(new ParseError(e));
        }
    }

}
