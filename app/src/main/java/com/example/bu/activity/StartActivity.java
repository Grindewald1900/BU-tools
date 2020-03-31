package com.example.bu.activity;

import android.os.Bundle;

import com.example.bu.R;
import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

public class StartActivity extends WelcomeActivity  {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
        .defaultBackgroundColor(R.color.colorPrimary)
        .page(new TitlePage(R.drawable.bk_bu_maps,
        getResources().getString(R.string.title_welcome_one))
        )
        .page(new TitlePage(R.drawable.bk_bu_sports,
        getResources().getString(R.string.title_welcome_two))
        )
        .page(new TitlePage(R.drawable.bk_bu_tools,
        getResources().getString(R.string.title_welcome_three))
        )
        .swipeToDismiss(true).canSkip(true)
        .build();
        }
}
