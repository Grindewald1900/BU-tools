package com.example.bu.ui.tool;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bu.R;
import com.example.bu.tool.WebviewUtils;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;

public class ToolsFragment extends Fragment {

    private Context mContext;
    private ToolsViewModel toolsViewModel;
    private QMUIRoundButton mBtOne, mBtTwo, mBtThree, mBtFour;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);
        mBtOne = root.findViewById(R.id.bt_tool_1);
        mBtTwo = root.findViewById(R.id.bt_tool_2);
        mBtThree = root.findViewById(R.id.bt_tool_3);
        mBtFour = root.findViewById(R.id.bt_tool_4);
        setButtonListener(mBtOne,mContext.getResources().getString(R.string.url_bu_directory));
        setButtonListener(mBtTwo,mContext.getResources().getString(R.string.url_bu_moodle));
        setButtonListener(mBtThree,mContext.getResources().getString(R.string.url_bu_mybu));
        setButtonListener(mBtFour,mContext.getResources().getString(R.string.url_bu_webmail));


        toolsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    private void setButtonListener(QMUIRoundButton btn, String url){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebviewUtils.openWebview(url,mContext);
            }
        });
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }
}
