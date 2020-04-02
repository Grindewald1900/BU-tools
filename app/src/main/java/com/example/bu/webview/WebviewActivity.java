package com.example.bu.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.example.bu.R;

public class WebviewActivity extends AppCompatActivity {
    private WebView mWebView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra(getResources().getString(R.string.URL));
        if(null == url){
            url = getResources().getString(R.string.url_bu_main);
        }
        mWebView = findViewById(R.id.web_webview);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl(url);
    }
}
