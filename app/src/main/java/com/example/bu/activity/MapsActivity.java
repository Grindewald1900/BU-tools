package com.example.bu.activity;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.bu.R;
import com.example.bu.tool.LocationConfig;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import net.grandcentrix.tray.AppPreferences;

import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appPreferences = new LocationConfig(this).getAppPreferences();
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
        double lat = Double.parseDouble(Objects.requireNonNull(appPreferences.getString("lat_main", "45.364387")));
        double lon = Double.parseDouble(Objects.requireNonNull(appPreferences.getString("lon_main","-71.845109")));

        // Add a marker in Sydney and move the camera
        LatLng bu = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(bu).title("Bishop's University").snippet("Location: main campus")).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bu,15.0f));
    }

}
