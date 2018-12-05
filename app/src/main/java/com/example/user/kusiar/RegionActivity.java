package com.example.user.kusiar;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toolbar;

public class RegionActivity extends AppCompatActivity {

    private ImageView regionJabar, regionJakarta;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);

    }
}
