package com.example.bu.ui.sports;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.bu.R;
import com.example.bu.webview.MyWebViewClient;

public class SportsFragment extends Fragment {

    private SportsViewModel sportsViewModel;
    private WebView mWebView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sportsViewModel =
                ViewModelProviders.of(this).get(SportsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sports, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);

        mWebView = root.findViewById(R.id.web_schedule);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("https://" + getActivity().getResources().getString(R.string.url_bu_schedule));


        sportsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
