package com.example.bu.tool;

import android.content.Context;
import android.content.Intent;

import com.example.bu.R;
import com.example.bu.webview.WebviewActivity;

public class WebviewUtils {
    public static void openWebview(String url, Context mContext){
        mContext.startActivity(new Intent(mContext, WebviewActivity.class).putExtra(mContext.getResources().getString(R.string.URL),url));
    }
}
