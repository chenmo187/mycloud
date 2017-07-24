package com.mycloud.net;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;

import java.util.Map;

/**
 * 作者 clarechen
 * 时间 2015/7/14
 */
public class GsonRequest<T> extends BaseRequest<T> {

    /**
     * Class type for the response
     */
    private final Class<T> mClass;


    /**
     * Callback for response delivery
     */
    private final Response.Listener<T> mListener;

    /**
     * @param method
     * 		Request type.. Method.GET etc
     * @param url
     * 		path for the requests
     * @param objectClass
     * 		expected class type for the response. Used by gson for serialization.
     * @param listener
     * 		handler for the response
     * @param errorListener
     * 		handler for errors
     */
    public GsonRequest(int method
            , String url
            , Map<String, String> params
            , Class<T> objectClass
            , Response.Listener<T> listener
            , Response.ErrorListener errorListener) {

        super(method, url,params,objectClass,listener, errorListener);
        this.mClass = objectClass;
        this.mListener = listener;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }
}