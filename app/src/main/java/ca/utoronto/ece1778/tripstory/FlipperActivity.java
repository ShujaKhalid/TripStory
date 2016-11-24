package ca.utoronto.ece1778.tripstory;

/**
 * Created by sssk9797 on 16/11/2016.
 */

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import app.num.numandroidpagecurleffect.PageCurlView;


public class FlipperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pageflipper);

        ArrayList stories = getIntent().getStringArrayListExtra("Stories");
        int position = getIntent().getIntExtra("position", 1);

        PageCurlViewzzz pageCurlViewzz = (PageCurlViewzzz) findViewById(R.id.pagecurl_view);
        System.out.println("Image Index = " + R.drawable.zoo);

        List<String> pages_id = stories;
        pageCurlViewzz.setCurlView(pages_id);

        //pageCurlViewzz.setCurlView(pages_id);
        //pageCurlViewzz.setCurlSpeed(500);

    }
}
