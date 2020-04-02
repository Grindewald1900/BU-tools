package com.example.bu.ui.settings;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.bu.R;
import com.example.bu.tool.WebviewUtils;
import com.leon.lib.settingview.LSettingItem;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SettingsFragment extends Fragment {

    private Context mContext;
    private SettingsViewModel mViewModel;
    private NiceSpinner niceSpinner1, niceSpinner2;
    private QMUIRoundButton mBtnOne, mBtnTwo, mBtnThree;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        mBtnThree = root.findViewById(R.id.bt_setting_3);

        niceSpinner1 = root.findViewById(R.id.spin_setting_1);
        niceSpinner2 = root.findViewById(R.id.spin_setting_2);
        List<String> dataSet1 = new LinkedList<>(Arrays.asList("English", "Chinese", "French"));
        List<String> dataSet2 = new LinkedList<>(Arrays.asList("BU Purple", "Black", "Red", "Yellow","Blue","Pink"));

        niceSpinner1.attachDataSource(dataSet1);
        niceSpinner2.attachDataSource(dataSet2);
        niceSpinner1.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                //Todo:
            }
        });
        mBtnThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewUtils.openWebview(mContext.getResources().getString(R.string.url_about_us),mContext);
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}
