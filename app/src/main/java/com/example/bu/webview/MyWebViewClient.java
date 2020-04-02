package com.example.bu.webview;

import android.os.Build;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;


public class MyWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Uri uri = Uri.parse(url);
        if (url.startsWith("file:") || uri.getHost() != null && uri.getHost().endsWith("example.com")) {
            return false;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return false;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
    {
        return false;
    }

}
