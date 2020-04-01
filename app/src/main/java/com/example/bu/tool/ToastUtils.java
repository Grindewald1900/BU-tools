package com.example.bu.tool;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static void baseToastLong(Context mContext, String s){
        Toast.makeText(mContext,s,Toast.LENGTH_LONG);
    }
}
