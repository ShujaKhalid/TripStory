package ca.utoronto.ece1778.tripstory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.PublicKey;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends Activity {

    private ImageButton img_b1;
    private ImageButton img_b2;
    private ImageButton img_b3;
    private ImageButton img_b4;
    private ImageView srory_frame;
    private ImageView img_view;
    private ImageView img_view2;
    private TextView StorylineText;
    public Bitmap userimage;
    public String userpal;
    public int userpal_int;

    public int currentfame;
    public int[] intarray = new int[10];

    public String[][] newstoryarray = new String[][]{
            {"who", "Who do you want to pick as a friend?", "f1_o1", "f1_o2", "f1_o3", "f1_o4", "You pick", "as a friend"},
            {"new_frame1", "Who do you want to pick as a friend?", "pic1", "pic2", "pic3", "pic4", "You pick", "as a friend"},
            {"frame2", "You and your friend find what?", "f2_o1", "f2_o2", "f2_o3", "f2_o4", "You and your friend find", ""},
            {"frame3", "Suddenly, you see a _________ coming from behind", "f3_o1", "f3_o2", "f3_o3", "f3_o4", "Suddenly, you see a", "coming from behind."},
            {"frame4", "Where do you go to hide away from it?", "f4_o1", "f4_o2", "f4_o3", "f4_o4", "So you hide away in a", "from it"},
            {"frame5", "There you find _________ stuck in a net", "f5_o1", "f5_o2", "f5_o3", "f5_o4", "There you find a", "stuck in a net"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout
        setContentView(R.layout.activity_main);

        currentfame = 1;
        // Get image of personalized user as a bitmap
        SharedPreferences sharedPref = this.getSharedPreferences("ca.utoronto.ece1778.tripstory" , this.MODE_PRIVATE);
        String personUser = sharedPref.getString("personaluser", "null");
        System.out.println(" personaluser = " + personUser); // If this yields null, the string wasn't accessed properly from shared preferences
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        options.inJustDecodeBounds = false;
        Bitmap result = BitmapFactory.decodeFile(personUser, options);
        userimage = Bitmap.createScaledBitmap(result,
                100, 100, false);


        img_view = (ImageView) findViewById(R.id.imageView);
        img_view2 = (ImageView) findViewById(R.id.imageView2);

        img_b1 = (ImageButton) findViewById(R.id.imageButton1);
        img_b2 = (ImageButton) findViewById(R.id.imageButton2);
        img_b3 = (ImageButton) findViewById(R.id.imageButton3);
        img_b4 = (ImageButton) findViewById(R.id.imageButton4);
        srory_frame = (ImageView) findViewById(R.id.storyFrame);
        StorylineText = (TextView) findViewById(R.id.StoryLineText);

        img_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentfame == 0) {
                    userpal = "f1_o1";
                    userpal_int = 2;
                }
                nextFrameSean();
            }
        });
        img_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentfame == 0) {
                    userpal = "f1_o2";
                    userpal_int = 3;
                }
                nextFrameSean();
            }
        });
        img_b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentfame == 0) {
                    userpal = "f1_o3";
                    userpal_int = 4;
                }
                nextFrameSean();
            }
        });
        img_b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentfame == 0) {
                    userpal = "f1_o3";
                    userpal_int = 5;
                }
                nextFrameSean();
            }
        });

//        SET INITIAL VIEW
        ininextFrameSean();
//        initialSean();

        img_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentfame == 1) {
                    userpal = "f1_o1";
                    userpal_int = 2;
                    System.out.println("Setting !!!");
                }
                intarray[currentfame] = 0;
                nextFrameSean();
            }
        });
        img_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The pal is chosen and used throughout
                if (currentfame == 1) {
                    userpal = "f1_o2";
                    userpal_int = 3;
                }
                intarray[currentfame] = 1;
                nextFrameSean();
            }
        });
        img_b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The pal is chosen and used throughout
                if (currentfame == 1) {
                    userpal = "f1_o3";
                    userpal_int = 4;
                }
                intarray[currentfame] = 2;
                nextFrameSean();
            }
        });
        img_b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // The pal is chosen and used throughout
                if (currentfame == 1) {
                    userpal = "f1_o4";
                    userpal_int = 5;
                }
                intarray[currentfame] = 3;
                nextFrameSean();
            }
        });
    }

    public void ininextFrameSean() {

        Toast.makeText(MainActivity.this, Integer.toString(userpal_int), Toast.LENGTH_SHORT).show();

        Drawable myDrawable_pal = getDrawable(getResources().getIdentifier(newstoryarray[0][userpal_int], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage_pal = ((BitmapDrawable) myDrawable_pal).getBitmap();
        img_view2.setImageBitmap(anImage_pal);

        // Set the user's image!
        img_view.setImageBitmap(userimage);

        Drawable myDrawable1 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage1 = ((BitmapDrawable) myDrawable1).getBitmap();
        img_b1.setImageBitmap(anImage1);

        Drawable myDrawable2 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][3], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage2 = ((BitmapDrawable) myDrawable2).getBitmap();
        img_b2.setImageBitmap(anImage2);

        Drawable myDrawable3 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][4], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage3 = ((BitmapDrawable) myDrawable3).getBitmap();
        img_b3.setImageBitmap(anImage3);

        Drawable myDrawable4 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][5], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage4 = ((BitmapDrawable) myDrawable4).getBitmap();
        img_b4.setImageBitmap(anImage4);

        Drawable FrameImg = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][0], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap frameImg = ((BitmapDrawable) FrameImg).getBitmap();
        srory_frame.setImageBitmap(frameImg);
        StorylineText.setText(newstoryarray[currentfame][1]);
    }

    public void nextFrameSean()
    {
        if (currentfame<newstoryarray.length-1){

            Toast.makeText(MainActivity.this,Integer.toString(userpal_int), Toast.LENGTH_SHORT).show();
            Drawable myDrawable_pal = getDrawable(getResources().getIdentifier(newstoryarray[0][userpal_int], "drawable", "ca.utoronto.ece1778.tripstory"));
            Bitmap anImage_pal = ((BitmapDrawable) myDrawable_pal).getBitmap();
            img_view2.setImageBitmap(anImage_pal);

            // Set the user's image!
            img_view.setImageBitmap(userimage);

            currentfame+=1;
            Drawable myDrawable1 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][2],"drawable","ca.utoronto.ece1778.tripstory"));
            Bitmap anImage1      = ((BitmapDrawable) myDrawable1).getBitmap();
            img_b1.setImageBitmap(anImage1);

            Drawable myDrawable2 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][3],"drawable","ca.utoronto.ece1778.tripstory"));
            Bitmap anImage2      = ((BitmapDrawable) myDrawable2).getBitmap();
            img_b2.setImageBitmap(anImage2);

            Drawable myDrawable3 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][4],"drawable","ca.utoronto.ece1778.tripstory"));
            Bitmap anImage3      = ((BitmapDrawable) myDrawable3).getBitmap();
            img_b3.setImageBitmap(anImage3);

            Drawable myDrawable4 = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][5],"drawable","ca.utoronto.ece1778.tripstory"));
            Bitmap anImage4      = ((BitmapDrawable) myDrawable4).getBitmap();
            img_b4.setImageBitmap(anImage4);

            Drawable FrameImg = getDrawable(getResources().getIdentifier(newstoryarray[currentfame][0],"drawable","ca.utoronto.ece1778.tripstory"));
            Bitmap frameImg      = ((BitmapDrawable) FrameImg).getBitmap();
            srory_frame.setImageBitmap(frameImg);

            StorylineText.setText(newstoryarray[currentfame][1]);

        } else {
            Intent intent = new Intent(MainActivity.this, Compile.class);
            intent.putExtra("myStoryint", intarray);
            intent.putExtra("buddy", userpal_int);
            startActivity(intent);
        }
    }
}
