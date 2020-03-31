package com.example.bu.ui.map;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bu.R;
import com.example.bu.activity.InitActivity;
import com.example.bu.activity.MapsActivity;
import com.example.bu.tool.ColorStateListMaker;
import com.example.bu.tool.LocationConfig;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetListItemView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.sql.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MapFragment extends Fragment {

    private Context mContext;
    private LocationConfig locationConfig;

    private MapViewModel mapViewModel;
    private QMUIBottomSheetListItemView bottomSheetListItemView;
    private QMUIRoundButton mBtGoto,mBtResult;
    private NiceSpinner niceSpinner1,niceSpinner2,niceSpinner3,niceSpinner4;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mBtGoto = root.findViewById(R.id.bt_map_goto);
        mBtResult = root.findViewById(R.id.bt_map_result);
        niceSpinner1 = root.findViewById(R.id.spin_map1);
        niceSpinner2 = root.findViewById(R.id.spin_map2);
        niceSpinner3 = root.findViewById(R.id.spin_map3);
        niceSpinner4 = root.findViewById(R.id.spin_map4);

        List<String> dataset1 = new LinkedList<>(Arrays.asList("ACADEMIC BUILDINGS","1-Astronomical Observatory","2-Bandeen Hall","3-Library Learning Commons","4-Centennial Theatre","5-Champlain College (CEGEP)","6-Cormier Centre (Dobson-Lagasse)",
                "7-Bishop Williams Hall","8-Foreman Art Gallery","9-Hamilton Building","10-Johnson Science Building","11-Marjorie Donald House (The SUB)","12-McGreer (Administration - Academic Offices)","13-Molson Fine Arts Building",
                "14-Morris House","15-Nicolls Arts Building","16-Turner Studio Theatre"));
        List<String> dataset2 = new LinkedList<>(Arrays.asList("SERVICES","1-Buildings & Grounds","2-Coulter Field","3-Golf Course","4-Hangar","5-Health Services","6-John H. Price Sports Centre","7-Panda Daycare",
                "8-Security","9-St. Mark's Chapel","10-University Bookstore","11-W.B. Scott Arena and Outdoor Pool"));
        List<String> dataset3 = new LinkedList<>(Arrays.asList("DINING FACILITIES"," Adam's Dining Room","2-Dewhurst Dining Hall","3-The Bus Stop Cafe","4-Tim Horton's","5-Gaiter Grill & Quiznos","6-Sport Center Coffee Shop"));
        List<String> dataset4 = new LinkedList<>(Arrays.asList(" RESIDENCES BUILDINGS","Abbott Residence","2-Kuehner Residence","3-Mackinnon Residence","4-Munster Residence","5-Norton Pollack Residence","6-Paterson Hall"));

        niceSpinner1.attachDataSource(dataset1);
        niceSpinner2.attachDataSource(dataset2);
        niceSpinner3.attachDataSource(dataset3);
        niceSpinner4.attachDataSource(dataset4);

        mBtGoto.setTextColor(ColorStateListMaker.createColorStateList(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.qmui_config_color_white),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary)
        ));

        setSpinnerListener(niceSpinner1);
        setSpinnerListener(niceSpinner2);
        setSpinnerListener(niceSpinner3);
        setSpinnerListener(niceSpinner4);

        mBtGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(mContext, MapsActivity.class));
            }
        });
        locationConfig = new LocationConfig(mContext);
//        bottomSheetListItemView = root.findViewById(R.id.fg_map_list);
        return root;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }

    private void setSpinnerListener(NiceSpinner spinner){
        spinner.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                String item = String.valueOf(parent.getItemAtPosition(position));
                Toast.makeText(mContext,"Your choice:"+item,Toast.LENGTH_LONG).show();
                mBtResult.setText(item);
            }
        });

    }
    

}
