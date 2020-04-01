package com.example.bu.tool;

import android.content.Context;

public class StringUtils {
    public static String getString(Context mContext, int id){
        return mContext.getResources().getString(id);
    }

}
