package ca.utoronto.ece1778.tripstory;

/**
 * Created by sssk9797 on 16/11/2016.
 */

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
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
        pageCurlViewzz.setChoice(position);
        pageCurlViewzz.setCurlView(pages_id);
        pageCurlViewzz.setCurlSpeed(500);

    }

}
