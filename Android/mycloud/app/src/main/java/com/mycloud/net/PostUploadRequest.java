package com.mycloud.net;


import android.text.TextUtils;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;


/**
 * 作者 clarechen
 * 时间 2015/7/14
 */
public class PostUploadRequest<T> extends BaseRequest<T> {

    /**
     * 正确数据的时候回掉用
     */
    private Response.Listener<T> mListener;
    /*请求 数据通过参数的形式传入*/
    private String LINEND = "\r\n";
    private String BOUNDARY = "--------------520-13-14"; //数据分隔线
    private String MULTIPART_FORM_DATA = "multipart/form-data";
    private String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded";
    private Map<String,String> params;
    private Map<String,String> fileParams;

    public PostUploadRequest(String url, Map<String, String> params, Map<String, String> fileParams, Class<T> mClass, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url,params,mClass, listener,errorListener);
        this.mListener = listener ;
        this.params = params;
        this.fileParams = fileParams;
        setShouldCache(false);
        //设置请求的响应事件，因为文件上传需要较长的时间，所以在这里加大了，设为5秒
        setRetryPolicy(new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream() ;
        try {
            if(params != null && params.size() > 0){
                StringBuffer sbParam = new StringBuffer();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    sbParam.append("--");
                    sbParam.append(BOUNDARY);
                    sbParam.append(LINEND);
                    sbParam.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
                    sbParam.append("Content-Type: text/plain; charset=utf-8" + LINEND);
                    sbParam.append("Content-Transfer-Encoding: 8bit" + LINEND);
                    sbParam.append(LINEND);
                    sbParam.append(entry.getValue());
                    sbParam.append(LINEND);
                }
                bos.write(sbParam.toString().getBytes("utf-8"));
            }

            //上传文件
            if(fileParams != null && fileParams.size() > 0){
                for (Map.Entry<String, String> fileEntry : fileParams.entrySet()){
                    File file = new File(fileEntry.getValue());
                    if(file.exists() && file.isFile()){
                        StringBuffer sb= new StringBuffer() ;
                        /*第一行*/
                        //`"--" + BOUNDARY + "\r\n"`
                        sb.append("--"+BOUNDARY);
                        sb.append("\r\n") ;
                        /*第二行*/
                        //Content-Disposition: form-data; name="参数的名称"; filename="上传的文件名" + "\r\n"
                        sb.append("Content-Disposition: form-data;");
                        sb.append(" name=\"");
                        if(TextUtils.isEmpty(fileEntry.getKey())){
                            sb.append(file.getName());
                        }else {
                            sb.append(fileEntry.getKey());
                        }
                        sb.append("\"");
                        sb.append("; filename=\"");
                        sb.append(file.getName());
                        sb.append("\"");
                        sb.append("\r\n") ;
                        /*第三行*/
                        //Content-Type: 文件的 mime 类型 + "\r\n"
                        sb.append("Content-Type: ");
                        sb.append("") ;
                        sb.append("\r\n") ;
                        /*第四行*/
                        //"\r\n"
                        sb.append("\r\n") ;

                        bos.write(sb.toString().getBytes("utf-8"));
                        /*第五行*/
                        //文件的二进制数据 + "\r\n"
                        bos.write(getBytes(file));
                        bos.write("\r\n".getBytes("utf-8"));
                    }
                }
            }

            /*结尾行*/
            //`"--" + BOUNDARY + "--" + "\r\n"`
            String endLine = "--" + BOUNDARY + "--" + "\r\n" ;

            bos.write(endLine.toString().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public static byte[] getBytes(File file){
        byte[] buffer = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }

    @Override
    public String getBodyContentType() {
        return MULTIPART_FORM_DATA+"; boundary="+BOUNDARY;
    }

}