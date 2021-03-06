package com.example.bu.tool;

import android.content.Context;

import com.example.bu.R;

import net.grandcentrix.tray.AppPreferences;
/**
 * Some config of locations need to be initialized
 * Keep the value of latitude and longitude in SharedPreference
 *
 * To fetch the values, you need to use getLat() and getLon() separately
 * because of different default value
 * */
public class LocationConfig {
    private Context mContext;
    private static AppPreferences appPreferences;
    /* default location value for campus */
    private final static String defaultLat = "45.364387";
    private final static String defaultLon = "-71.845109";

    public LocationConfig(Context mContext) {
        this.mContext = mContext;
        appPreferences =  new AppPreferences(mContext);
        appPreferences.put("lon_main", defaultLon);
        appPreferences.put("lat_main", defaultLat);

        addLocation(R.string.A1,"-71.847621","45.366264");
        addLocation(R.string.A2,"-71.848379","45.365214");
        addLocation(R.string.A3,"-71.848833","45.364962");
        addLocation(R.string.A4,"-71.847378","45.364863");
        addLocation(R.string.A5,"-71.844978","45.366217");
        addLocation(R.string.A6,"-71.844957","45.365482");
        addLocation(R.string.A7,"-71.848330","45.366134");
        addLocation(R.string.A8,"-71.847061","45.364888");
        addLocation(R.string.A9,"-71.847881","45.366064");
        addLocation(R.string.A10,"-71.848311","45.365914");
        addLocation(R.string.A11,"-71.846199","45.365176");
        addLocation(R.string.A12,"-71.849200","45.365732");
        addLocation(R.string.A13,"-71.847199","45.364354");
        addLocation(R.string.A14,"-71.847089","45.365696");
        addLocation(R.string.A15,"-71.847384","45.366192");
        addLocation(R.string.A16,"-71.847722","45.364826");

        addLocation(R.string.B1,"-71.848070","45.364861");
        addLocation(R.string.B2,"-71.842495","45.365413");
        addLocation(R.string.B3,"-71.846570","45.359819");
        addLocation(R.string.B4,"-71.842602","45.361925");
        addLocation(R.string.B5,"-71.842457","45.363444");
        addLocation(R.string.B6,"-71.841717","45.363998");
        addLocation(R.string.B7,"-71.843657","45.365304");
        addLocation(R.string.B8,"-71.363676","45.363676");
        addLocation(R.string.B9,"-71.849005","45.365988");
        addLocation(R.string.B10,"-71.846528","45.365195");
        addLocation(R.string.B11,"-71.847210","45.363811");

        addLocation(R.string.C1,"-71.844352","45.363673");
        addLocation(R.string.C2,"-71.844274","45.363511");
        addLocation(R.string.C3,"-71.847625","45.366342");
        addLocation(R.string.C4,"-71.846511","45.365004");
        addLocation(R.string.C5,"-71.846162","45.365017");
        addLocation(R.string.C6,"-71.841248","45.363859");

        addLocation(R.string.D1,"-71.844929","45.363331");
        addLocation(R.string.D2,"-71.843540","45.363274");
        addLocation(R.string.D3,"-71.845026","45.364295");
        addLocation(R.string.D4,"-71.844232","45.362863");
        addLocation(R.string.D5,"-71.847939","45.365377");
        addLocation(R.string.D6,"-71.843838","45.362243");



    }

    public static void addString(String key, String value){
        appPreferences.put(key,value);
    }

    public static String getLat(String key){
        return appPreferences.getString(key,defaultLat);
    }

    public static String getLon(String key){
        return appPreferences.getString(key,defaultLon);
    }

    public static AppPreferences getAppPreferences(){
        return appPreferences;
    }

    private void addLocation(int id, String lon, String lat){
        appPreferences.put(StringUtils.getString(mContext, id)+"Lon", lon);
        appPreferences.put(StringUtils.getString(mContext, id)+"Lat", lat);
    }
}
