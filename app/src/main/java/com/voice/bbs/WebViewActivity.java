package com.voice.bbs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.voice.R;

/**
 * Created by EXcalibur on 2017/5/20.
 */

public class WebViewActivity extends AppCompatActivity {
    private String BBS_URL ="BBS_URL";
    private String BBS_ID ="BBS_ID";
    private String BBS_IDENTIEF ="BBS_IDENTIFY";
    private WebView webView;
    private String identify;
    private String id;
    private String urlString;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        identify = (String)this.getIntent().getSerializableExtra(BBS_IDENTIEF);
        id=(String)this.getIntent().getSerializableExtra(BBS_ID);
        urlString=(String)this.getIntent().getSerializableExtra(BBS_URL);
        setContentView(R.layout.activity_webview);
        init();
    }
    private void init(){
        webView = (WebView) findViewById(R.id.webView);
        //WebView加载web资源
        webView.loadUrl(urlString);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}

