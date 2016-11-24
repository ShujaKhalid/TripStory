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
import android.widget.LinearLayout;
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
    public LinearLayout bgElement;

    public int currentfame;
    public int[] intarray = new int[15];

    public String[][] newstoryarray = new String[][]{
            {"who", "Who do you want to pick as a friend?", "f1o1", "f1o2", "f1o3", "f1o4", "I picked an", " as a friend"},
            {"f1", "Who do you want to pick as a friend?", "f1o1", "f1o2", "f1o3", "f1o4", "I picked an", " as a friend"},
            {"f2", "You and your friend find what?", "f2o1", "f2o2", "f2o3", "f2o4", "Me and my friend found a ", ""},
            {"f3", "Suddenly, you see a _________ coming from behind", "f3o1", "f3o2", "f3o3", "f3o4", "Suddenly, I saw a ", " coming from behind."},
            {"f4", "Where do you go to hide away from it?", "f4o1", "f4o2", "f4o3", "f4o4", "So I hid away in a ", " from it"},
            {"f5", "There you find _________ stuck in a net", "f5o1", "f5o2", "f5o3", "f5o4", "There I found a ", " stuck in a net"},
            {"f6", "You use a ________ to cut the net and set it free", "f6o1", "f6o2", "f6o3", "f6o4", "So I used a ", "to cut the net and set it free"},
            {"f7", "You received a __________ as a gift for helping", "f7o1", "f7o2", "f7o3", "f7o4", "I then received a ", " as a gift for helping"},
            {"f8", "A mermaid met you and took you to the ________", "f8o1", "f8o2", "f8o3", "f8o4", "A mermaid met me and took you to the ", ""},
            {"f9", "There you started following the  ________", "f9o1", "f9o2", "f9o3", "f9o4", "There i started following the  ", ""},
            {"f10", "They take you to a treasure map of the ________ ", "f10o1", "f10o2", "f10o3", "f10o4", "They took me to a treasure map of the  ", ""}
    };

    public String[] bkgs = new String[]{
            "", "bkg1", "bkg2", "bkg3", "bkg4"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout
        setContentView(R.layout.activity_main);

        LinearLayout bgElement = (LinearLayout) findViewById(R.id.main_bkg);
        currentfame = 1;
        // Get image of personalized user as a bitmap
        SharedPreferences sharedPref = this.getSharedPreferences("ca.utoronto.ece1778.tripstory" , this.MODE_PRIVATE);
        String personUser = sharedPref.getString("personaluser", "null");
        //System.out.println(" personaluser = " + personUser); // If this yields null, the string wasn't accessed properly from shared preferences
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        options.inJustDecodeBounds = false;
        Bitmap result = BitmapFactory.decodeFile(personUser, options);
        Bitmap orientedBitmap = ExifUtil.rotateBitmap(personUser, result);
        userimage = Bitmap.createScaledBitmap(orientedBitmap,
                100, 100, false);


        img_view = (ImageView) findViewById(R.id.imageView);
        img_view2 = (ImageView) findViewById(R.id.imageView2);

        img_b1 = (ImageButton) findViewById(R.id.imageButton1);
        img_b2 = (ImageButton) findViewById(R.id.imageButton2);
        img_b3 = (ImageButton) findViewById(R.id.imageButton3);
        img_b4 = (ImageButton) findViewById(R.id.imageButton4);
        srory_frame = (ImageView) findViewById(R.id.storyFrame);
        StorylineText = (TextView) findViewById(R.id.StoryLineText);

//        img_b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentfame == 0) {
//                    userpal = "f1_o1";
//                    userpal_int = 2;
//                }
//                nextFrameSean();
//            }
//        });
//        img_b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentfame == 0) {
//                    userpal = "f1_o2";
//                    userpal_int = 3;
//                }
//                nextFrameSean();
//            }
//        });
//        img_b3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentfame == 0) {
//                    userpal = "f1_o3";
//                    userpal_int = 4;
//                }
//                nextFrameSean();
//            }
//        });
//        img_b4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (currentfame == 0) {
//                    userpal = "f1_o3";
//                    userpal_int = 5;
//                }
//                nextFrameSean();
//            }
//        });

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
//        bgElement.setBackground(this.getResources().getDrawable(R.drawable.bkg1));
    }

    public void ininextFrameSean() {
//
//        Toast.makeText(MainActivity.this, Integer.toString(userpal_int), Toast.LENGTH_SHORT).show();

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

//        Drawable bkg_img = getDrawable(getResources().getIdentifier(bkgs[currentfame], "drawable", "ca.utoronto.ece1778.tripstory"));
//        bgElement.setBackground(bkg_img);
    }

    public void nextFrameSean()
    {
        if (currentfame<newstoryarray.length-1){

//            Toast.makeText(MainActivity.this,Integer.toString(userpal_int), Toast.LENGTH_SHORT).show();
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

//            Drawable bkg_img = getDrawable(getResources().getIdentifier(bkgs[currentfame], "drawable", "ca.utoronto.ece1778.tripstory"));
//            bgElement.setBackground(bkg_img);

        } else {
            Intent intent = new Intent(MainActivity.this, Compile.class);
            intent.putExtra("myStoryint", intarray);
            intent.putExtra("buddy", userpal_int);
            startActivity(intent);
        }
    }
}
