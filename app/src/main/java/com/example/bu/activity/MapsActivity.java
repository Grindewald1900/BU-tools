package com.example.bu.activity;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.RequestResult;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.bu.R;
import com.example.bu.tool.LocationConfig;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppPreferences appPreferences;
    private String locationName, serviceName, provider;
    private LocationManager locationManager;
    private Context mContext;
    double myLon, myLat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        locationName = getIntent().getStringExtra("Location");
        Toast.makeText(this, locationName, Toast.LENGTH_LONG).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appPreferences = new LocationConfig(this).getAppPreferences();
        serviceName = Context.LOCATION_SERVICE;
        provider = LocationManager.GPS_PROVIDER;
        locationManager = (LocationManager) getSystemService(serviceName);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if(null != location){
            myLon = location.getLongitude();
            myLat = location.getLatitude();
        }else {
            myLon = -71.847621;
            myLat = 45.366264;
        }

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double lat = Double.parseDouble(Objects.requireNonNull(appPreferences.getString(locationName+"Lat", "45.364387")));
        double lon = Double.parseDouble(Objects.requireNonNull(appPreferences.getString(locationName+"Lon","-71.845109")));

        // Add a marker in Sydney and move the camera
        LatLng bu = new LatLng(lat, lon);
        LatLng me = new LatLng(myLat, myLon);
        mMap.addMarker(new MarkerOptions().position(bu).title(locationName).snippet("Lon:"+lon+" Lat:"+lat)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bu,16.0f));

        GoogleDirection.withServerKey(getResources().getString(R.string.google_map_key))
                .from(me)
                .to(bu)
                .transitMode(TransportMode.WALKING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction) {
                        String status = direction.getStatus();
                        if(status.equals(RequestResult.OK)) {
//                            Route route = direction.getRouteList().get(0);
                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(mContext, stepList, 5, Color.RED, 3, Color.BLUE);
                            for (PolylineOptions polylineOption : polylineOptionList) {
                                mMap.addPolyline(polylineOption);
                            }
                            // Do something
                        } else if(status.equals(RequestResult.NOT_FOUND)) {
                            Toast.makeText(mContext,"Not Found",Toast.LENGTH_LONG).show();
                            // Do something
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {
                        Toast.makeText(mContext,"Failed to get direction",Toast.LENGTH_LONG).show();
                    }
                });
    }

}
