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
import com.example.bu.tool.StringUtils;
import com.example.bu.tool.ToastUtils;
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
    private Boolean isChosen = false;

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

        List<String> dataset1 = new LinkedList<>(Arrays.asList("ACADEMIC BUILDINGS",
                StringUtils.getString(mContext,R.string.A1),
                StringUtils.getString(mContext,R.string.A2),
                StringUtils.getString(mContext,R.string.A3),
                StringUtils.getString(mContext,R.string.A4),
                StringUtils.getString(mContext,R.string.A5),
                StringUtils.getString(mContext,R.string.A6),
                StringUtils.getString(mContext,R.string.A7),
                StringUtils.getString(mContext,R.string.A8),
                StringUtils.getString(mContext,R.string.A9),
                StringUtils.getString(mContext,R.string.A10),
                StringUtils.getString(mContext,R.string.A11),
                StringUtils.getString(mContext,R.string.A12),
                StringUtils.getString(mContext,R.string.A13),
                StringUtils.getString(mContext,R.string.A14),
                StringUtils.getString(mContext,R.string.A15),
                StringUtils.getString(mContext,R.string.A16)));

        List<String> dataset2 = new LinkedList<>(Arrays.asList("SERVICES",
                StringUtils.getString(mContext,R.string.B1),
                StringUtils.getString(mContext,R.string.B2),
                StringUtils.getString(mContext,R.string.B3),
                StringUtils.getString(mContext,R.string.B4),
                StringUtils.getString(mContext,R.string.B5),
                StringUtils.getString(mContext,R.string.B6),
                StringUtils.getString(mContext,R.string.B7),
                StringUtils.getString(mContext,R.string.B8),
                StringUtils.getString(mContext,R.string.B9),
                StringUtils.getString(mContext,R.string.B10),
                StringUtils.getString(mContext,R.string.B11)
                ));

        List<String> dataset3 = new LinkedList<>(Arrays.asList("DINING FACILITIES",
                StringUtils.getString(mContext,R.string.C1),
                StringUtils.getString(mContext,R.string.C2),
                StringUtils.getString(mContext,R.string.C3),
                StringUtils.getString(mContext,R.string.C4),
                StringUtils.getString(mContext,R.string.C5),
                StringUtils.getString(mContext,R.string.C6)
                ));

        List<String> dataset4 = new LinkedList<>(Arrays.asList(" RESIDENCES BUILDINGS",
                StringUtils.getString(mContext,R.string.D1),
                StringUtils.getString(mContext,R.string.D2),
                StringUtils.getString(mContext,R.string.D3),
                StringUtils.getString(mContext,R.string.D4),
                StringUtils.getString(mContext,R.string.D5),
                StringUtils.getString(mContext,R.string.D6)
                ));

        niceSpinner1.attachDataSource(dataset1);
        niceSpinner2.attachDataSource(dataset2);
        niceSpinner3.attachDataSource(dataset3);
        niceSpinner4.attachDataSource(dataset4);
        setSpinnerListener(niceSpinner1);
        setSpinnerListener(niceSpinner2);
        setSpinnerListener(niceSpinner3);
        setSpinnerListener(niceSpinner4);

        mBtGoto.setTextColor(ColorStateListMaker.createColorStateList(
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.qmui_config_color_white),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimary)
        ));

        mBtGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isChosen){
                    getActivity().startActivity(new Intent(mContext, MapsActivity.class).putExtra("Location",mBtResult.getText().toString()));
                }else {
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.select_again),Toast.LENGTH_LONG).show();
                }
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
                if(0 == position){
                    isChosen = false;
                    Toast.makeText(mContext,mContext.getResources().getString(R.string.select_again),Toast.LENGTH_LONG).show();
                }else {
                    isChosen = true;
                    String item = String.valueOf(parent.getItemAtPosition(position));
                    Toast.makeText(mContext,"Your choice: "+item ,Toast.LENGTH_LONG).show();
                    mBtResult.setText(item);
                }
            }
        });
    }



}
