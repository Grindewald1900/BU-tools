package com.example.bu.ui.sports;

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

public class SportsFragment extends Fragment {

    private SportsViewModel sportsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sportsViewModel =
                ViewModelProviders.of(this).get(SportsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sports, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        sportsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
