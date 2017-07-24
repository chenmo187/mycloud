package com.mycloud.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mycloud.R;

/**
 * Created by clarechen on 2015/12/15.
 */
public class WebViewActivity extends BaseActivity {
    private WebView webView;
    private ProgressBar progressBar;

    @Override
    public void initView() {
        webView = (WebView) findViewById(R.id.webView_id);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_webview_load);
        setHeaderAndBack(getIntent().getStringExtra("title"));
        initWebView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initView();
    }


    private void initWebView() {

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100) {
                    progressBar.setVisibility(View.GONE);
                }
                progressBar.setProgress(newProgress);

            }
        });

        webView.loadUrl(getIntent().getStringExtra("url"));

        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }
        };

        webView.setWebViewClient(client);
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // 建议缓存策略为，判断是否有网络，有的话，使用LOAD_DEFAULT,无网络时，使用LOAD_CACHE_ELSE_NETWORK
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        // 开启DOM storage API 功能
        setting.setDomStorageEnabled(true);
        // 开启database storage API功能
        setting.setDatabaseEnabled(true);
    }

}
