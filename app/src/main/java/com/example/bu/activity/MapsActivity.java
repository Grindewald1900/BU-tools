package com.example.bu.activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
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
import com.example.bu.tool.LocationUtils;
import com.example.bu.tool.ToastUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopup;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import net.grandcentrix.tray.AppPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private AppPreferences appPreferences;
    private String locationName, serviceName, provider;
    private LocationManager locationManager;
    private Context mContext;
    private double myLon, myLat, lon, lat;
    private QMUIRoundButton mBtChangeMode;
    private QMUIPopup popup;
    private LatLng me, bu;
    private BitmapDescriptor bitmapDescriptor,bitmapDescriptor2;
//    private ConstraintLayout layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mContext = this;
        locationName = getIntent().getStringExtra("Location");
//        Toast.makeText(this, locationName, Toast.LENGTH_LONG).show();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mBtChangeMode = findViewById(R.id.bt_map_change_mode);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appPreferences = new LocationConfig(this).getAppPreferences();

        mBtChangeMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });

        Location location = LocationUtils.getLocation(mContext);
        if(null != location){
            myLon = location.getLongitude();
            myLat = location.getLatitude();
        }else {
            ToastUtils.baseToastLong(mContext,getResources().getString(R.string.fail_location));
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
        lat = Double.parseDouble(Objects.requireNonNull(appPreferences.getString(locationName+"Lat", "45.364387")));
        lon = Double.parseDouble(Objects.requireNonNull(appPreferences.getString(locationName+"Lon","-71.845109")));
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.icon_location_64);
        bitmapDescriptor2 = BitmapDescriptorFactory.fromResource(R.drawable.icon_me_64);

        bu = new LatLng(lat, lon);
        me = new LatLng(myLat, myLon);


        // Walking as default
        direction(me,bu,TransportMode.WALKING,R.color.colorPrimary);

    }

    private void direction(LatLng start, LatLng end, String mode,int color){
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(me).icon(bitmapDescriptor2));
        mMap.addMarker(new MarkerOptions().position(bu).title(locationName).snippet("Lon:"+lon+" Lat:"+lat).icon(bitmapDescriptor)).showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(bu,16.0f));
        GoogleDirection.withServerKey(getResources().getString(R.string.google_direction_webService_key))
                .from(start)
                .to(end)
                .transitMode(mode)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction) {
                        String status = direction.getStatus();
                        if(status.equals(RequestResult.OK)) {
//                            Route route = direction.getRouteList().get(0);
                            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
                            ArrayList<PolylineOptions> polylineOptionList = DirectionConverter.createTransitPolyline(mContext, stepList, 5, getResources().getColor(color), 3, Color.RED);
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

    private void showPopup(View v){
        String[] listItems = new String[]{"Driving", "Walking", "Bicycle"};
        List<String> data = new ArrayList<>();
        Collections.addAll(data, listItems);

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_list_item, data);
        AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){
                    mBtChangeMode.setText(getResources().getString(R.string.change_trans_mode2));
                    Toast.makeText(mContext,"Transport mode: Driving",Toast.LENGTH_LONG).show();
                    direction(me,bu,TransportMode.DRIVING,R.color.qmui_config_color_red);
                }else if(i == 1){
                    mBtChangeMode.setText(getResources().getString(R.string.change_trans_mode));
                    Toast.makeText(mContext,"Transport mode: Walking",Toast.LENGTH_LONG).show();
                    direction(me,bu,TransportMode.WALKING,R.color.colorPrimary);
                }else if(i == 2){
                    mBtChangeMode.setText(getResources().getString(R.string.change_trans_mode3));
                    Toast.makeText(mContext,"Transport mode: Bicycle",Toast.LENGTH_LONG).show();
                    direction(me,bu,TransportMode.BICYCLING,R.color.qmui_config_color_blue);
                }
                if (popup != null) {
                    popup.dismiss();
                }
            }
        };
        popup = QMUIPopups.listPopup(this,
                QMUIDisplayHelper.dp2px(this, 250),
                QMUIDisplayHelper.dp2px(this, 300),
                adapter,
                onItemClickListener)
                .animStyle(QMUIPopup.ANIM_GROW_FROM_CENTER)
                .preferredDirection(QMUIPopup.DIRECTION_TOP)
                .shadow(true)
                .edgeProtection(QMUIDisplayHelper.dp2px(this, 10))
                .offsetYIfTop(QMUIDisplayHelper.dp2px(this, 5))
                .show(v);
    }

}
