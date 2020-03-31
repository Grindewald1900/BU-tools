package com.example.bu.tool;

import android.content.Context;

import net.grandcentrix.tray.AppPreferences;

public class LocationConfig {
    private Context mContext;
    private final AppPreferences appPreferences;

    public LocationConfig(Context mContext) {
        this.mContext = mContext;
        appPreferences =  new AppPreferences(mContext);
        appPreferences.put("lon_main", "-71.845109");
        appPreferences.put("lat_main", "45.364387");
    }

    public AppPreferences getAppPreferences(){
        return this.appPreferences;
    }
}
