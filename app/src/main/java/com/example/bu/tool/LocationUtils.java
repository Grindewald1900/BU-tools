package com.example.bu.tool;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;

import androidx.core.app.ActivityCompat;

/**
 * If GPS available, use GPS.
 * Else if network available, use network
 */
public class LocationUtils {
    private static LocationManager locationManager;
    private static Context mContext;

    public static Location getLocation(Context context) {
        mContext = context;

        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (!gpsEnabled() && !netWorkEnabled()) {
            ToastUtils.baseToastLong(mContext,"Please open your GPS module");
        }

        if (gpsEnabled() && getGPSLocation(locationManager) != null) {
            return getGPSLocation(locationManager);

        } else if (netWorkEnabled() && getNetWorkLocation(locationManager) != null) {
            return getNetWorkLocation(locationManager);

        } else {
            return null;
        }

    }

    private static boolean gpsEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private static boolean netWorkEnabled() {
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private static Location getGPSLocation(LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private static Location getNetWorkLocation(LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }

}
