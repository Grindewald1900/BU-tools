package com.example.bu.ui.map;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bu.R;
import com.example.bu.activity.InitActivity;
import com.example.bu.activity.MapsActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheetListItemView;

public class MapFragment extends Fragment {

    private Context mContext;

    private MapViewModel mapViewModel;
    private QMUIBottomSheetListItemView bottomSheetListItemView;
    private Button mBtGoto;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mapViewModel = ViewModelProviders.of(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map, container, false);
        mBtGoto = root.findViewById(R.id.bt_map_goto);

        mBtGoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(mContext, InitActivity.class));
            }
        });
//        bottomSheetListItemView = root.findViewById(R.id.fg_map_list);
        return root;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }


}
