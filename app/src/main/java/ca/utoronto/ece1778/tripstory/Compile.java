package ca.utoronto.ece1778.tripstory;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ayazhan on 11/2/2016.
 */

public class Compile extends AppCompatActivity {

    // Create a storage reference from our app
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://tripstory-47cf0.appspot.com");
    private UploadTask uploadTask;
    private TextView story;
    private Button back;
    private Context mContext;
    public File myPath;

    private TextView text1, text2,
            text1_2, text2_2,
            text1_3, text2_3, text3_3,
            text1_4, text2_4,
            text1_5, text2_5,
            text1_6, text2_6, text3_6,
            text1_7, text2_7, text3_7,
            text1_8, text2_8,
            text1_9, text2_9,
            text1_10, text2_10,
            text1_11, text2_11,
            text1_12, text2_12, text3_12,
            text1_13, text2_13,
            text1_14, text2_14,
            text1_15, text2_15;

    private int[] arrayB;
    private int buddy;
    private ImageView pic1, pic2, pic3, pic4, pic5, pic6, pic7, pic8, pic9, pic10, pic11, pic12, pic13, pic14, pic15;

    public String[][] currstory = new String[][]{
            {"f1", "Who do you want to pick as a friend?", "f1o1", "f1o2", "f1o3", "f1o4", "I picked an", " as a friend"},
            {"f2", "You and your friend find what?", "f2o1", "f2o2", "f2o3", "f2o4", "Me and my friend found a ", ""},
            {"f3", "Suddenly, you see a _________ coming from behind", "f3o1", "f3o2", "f3o3", "f3o4", "Suddenly, I saw a ", " coming from", " behind."},
            {"f4", "Where do you go to hide away from it?", "f4o1", "f4o2", "f4o3", "f4o4", "So I hid away in a ", " from it"},
            {"f5", "There you find _________ stuck in a net", "f5o1", "f5o2", "f5o3", "f5o4", "There I found a ", " stuck in a net"},
            {"f6", "You use a ________ to cut the net and set it free", "f6o1", "f6o2", "f6o3", "f6o4", "So I used a ", "to cut the net and", " set it free"},
            {"f7", "You received a __________ as a gift for helping", "f7o1", "f7o2", "f7o3", "f7o4", "I then received a ", " as a gift for", "helping"},
            {"f8", "A mermaid met you and took you to the ________", "f8o1", "f8o2", "f8o3", "f8o4", "A mermaid took you to the ", ""},
            {"f9", "There you started following the  ________", "f9o1", "f9o2", "f9o3", "f9o4", "There i started following the  ", ""},
            {"f10", "They take you to a treasure map of the ________ ", "f10o1", "f10o2", "f10o3", "f10o4", "They took me to a treasure map of the  ", ""},
            {"f11", "A rainbow fish took you back to __________", "f8o1", "f8o2", "f8o3", "f8o4", "A rainbow fish took me back to ", ""},
            {"f12", "There you found a ______ hiding in a sand castle.", "f12o1", "f12o2", "f12o3", "f12o4", "There I found a ", " hiding in a", " sand castle."},
            {"f13", "It took you surfing on a ________", "f13o1", "f13o2", "f13o3", "f13o4", "It took me surfing on a ", ""},
            {"f14", "You reached the shore and see your ______", "f14o1", "f14o2", "f14o3", "f14o4", "I reached the shore and see my ", ""},
            {"f15", "You use it to go __________", "f15o1", "f15o2", "f15o3", "f15o4", "I used it to go ", ""}

    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the layout
        setContentView(R.layout.compile_layout);

        arrayB = getIntent().getIntArrayExtra("myStoryint");
        buddy = getIntent().getIntExtra("buddy", 0);

//        for (int i = 0; i<arrayB.length; i++){
//            Toast.makeText(Compile.this, "Option was "+arrayB[i], Toast.LENGTH_SHORT).show();
//        }


        text1 = (TextView) findViewById(R.id.text1);
        text2 = (TextView) findViewById(R.id.text2);
        text1_2 = (TextView) findViewById(R.id.text1_2);
        text2_2 = (TextView) findViewById(R.id.text2_2);

        text1_3 = (TextView) findViewById(R.id.text1_3);
        text2_3 = (TextView) findViewById(R.id.text2_3);
        text3_3 = (TextView) findViewById(R.id.text3_3);
        text1_4 = (TextView) findViewById(R.id.text1_4);
        text2_4 = (TextView) findViewById(R.id.text2_4);
        text1_5 = (TextView) findViewById(R.id.text1_5);
        text2_5 = (TextView) findViewById(R.id.text2_5);
        text1_6 = (TextView) findViewById(R.id.text1_6);
        text2_6 = (TextView) findViewById(R.id.text2_6);
        text3_6 = (TextView) findViewById(R.id.text3_6);
        text1_7 = (TextView) findViewById(R.id.text1_7);
        text2_7 = (TextView) findViewById(R.id.text2_7);
        text3_7 = (TextView) findViewById(R.id.text3_7);
        text1_8 = (TextView) findViewById(R.id.text1_8);
        text2_8 = (TextView) findViewById(R.id.text2_8);
        text1_9 = (TextView) findViewById(R.id.text1_9);
        text2_9 = (TextView) findViewById(R.id.text2_9);
        text1_10 = (TextView) findViewById(R.id.text1_10);
        text2_10 = (TextView) findViewById(R.id.text2_10);


        text1_11 = (TextView) findViewById(R.id.text1_11);
        text2_11 = (TextView) findViewById(R.id.text2_11);
        text1_12 = (TextView) findViewById(R.id.text1_12);
        text2_12 = (TextView) findViewById(R.id.text2_12);
        text3_12 = (TextView) findViewById(R.id.text3_12);
        text1_13 = (TextView) findViewById(R.id.text1_13);
        text2_13 = (TextView) findViewById(R.id.text2_13);
        text1_14 = (TextView) findViewById(R.id.text1_14);
        text2_14 = (TextView) findViewById(R.id.text2_14);
        text1_15 = (TextView) findViewById(R.id.text1_15);
        text2_15 = (TextView) findViewById(R.id.text2_15);

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic4 = (ImageView) findViewById(R.id.pic4);
        pic5 = (ImageView) findViewById(R.id.pic5);
        pic6 = (ImageView) findViewById(R.id.pic6);
        pic7 = (ImageView) findViewById(R.id.pic7);
        pic8 = (ImageView) findViewById(R.id.pic8);
        pic9 = (ImageView) findViewById(R.id.pic9);
        pic10 = (ImageView) findViewById(R.id.pic10);

        pic11 = (ImageView) findViewById(R.id.pic11);
        pic12 = (ImageView) findViewById(R.id.pic12);
        pic13 = (ImageView) findViewById(R.id.pic13);
        pic14 = (ImageView) findViewById(R.id.pic14);
        pic15 = (ImageView) findViewById(R.id.pic15);

//        Bundle extras = getIntent().getExtras();
//        int[] arrayB = extras.getIntArray("myStoryint");



//        Bundle b=this.getIntent().getExtras();
//        String[] array=b.getStringArray(key);

        Drawable myDrawable1 = getDrawable(getResources().getIdentifier(currstory[0][buddy],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage1      = ((BitmapDrawable) myDrawable1).getBitmap();
        text1.setText(currstory[0][6]);
        pic1.setImageBitmap(anImage1);
        text2.setText(currstory[0][7]);

        Drawable myDrawable2 = getDrawable(getResources().getIdentifier(currstory[1][arrayB[2]+2],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage2      = ((BitmapDrawable) myDrawable2).getBitmap();
        text1_2.setText(currstory[1][6]);
        pic2.setImageBitmap(anImage2);
        text2_2.setText(currstory[1][7]);

        Drawable myDrawable3 = getDrawable(getResources().getIdentifier(currstory[2][arrayB[3]+2],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage3      = ((BitmapDrawable) myDrawable3).getBitmap();
        text1_3.setText(currstory[2][6]);
        pic3.setImageBitmap(anImage3);
        text2_3.setText(currstory[2][7]);
        text3_3.setText(currstory[2][8]);

        Drawable myDrawable4 = getDrawable(getResources().getIdentifier(currstory[3][arrayB[4]+2],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage4      = ((BitmapDrawable) myDrawable4).getBitmap();
        text1_4.setText(currstory[3][6]);
        pic4.setImageBitmap(anImage4);
        text2_4.setText(currstory[3][7]);

        Drawable myDrawable5 = getDrawable(getResources().getIdentifier(currstory[4][arrayB[5]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage5 = ((BitmapDrawable) myDrawable5).getBitmap();
        text1_5.setText(currstory[4][6]);
        pic5.setImageBitmap(anImage5);
        text2_5.setText(currstory[4][7]);


        Drawable myDrawable6 = getDrawable(getResources().getIdentifier(currstory[5][arrayB[6]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage6 = ((BitmapDrawable) myDrawable6).getBitmap();
        text1_6.setText(currstory[5][6]);
        pic6.setImageBitmap(anImage6);
        text2_6.setText(currstory[5][7]);
        text3_6.setText(currstory[5][8]);

        Drawable myDrawable7 = getDrawable(getResources().getIdentifier(currstory[6][arrayB[7]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage7 = ((BitmapDrawable) myDrawable7).getBitmap();
        text1_7.setText(currstory[6][6]);
        pic7.setImageBitmap(anImage7);
        text2_7.setText(currstory[6][7]);
        text3_7.setText(currstory[6][8]);

        Drawable myDrawable8 = getDrawable(getResources().getIdentifier(currstory[7][arrayB[8]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage8 = ((BitmapDrawable) myDrawable8).getBitmap();
        text1_8.setText(currstory[7][6]);
        pic8.setImageBitmap(anImage8);
        text2_8.setText(currstory[7][7]);

        Drawable myDrawable9 = getDrawable(getResources().getIdentifier(currstory[8][arrayB[9]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage9 = ((BitmapDrawable) myDrawable9).getBitmap();
        text1_9.setText(currstory[8][6]);
        pic9.setImageBitmap(anImage9);
        text2_9.setText(currstory[8][7]);

        Drawable myDrawable10 = getDrawable(getResources().getIdentifier(currstory[9][arrayB[10]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage10 = ((BitmapDrawable) myDrawable10).getBitmap();
        text1_10.setText(currstory[9][6]);
        pic10.setImageBitmap(anImage10);
        text2_10.setText(currstory[9][7]);


        Drawable myDrawable11 = getDrawable(getResources().getIdentifier(currstory[10][arrayB[11]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage11 = ((BitmapDrawable) myDrawable11).getBitmap();
        text1_11.setText(currstory[10][6]);
        pic11.setImageBitmap(anImage11);
        text2_11.setText(currstory[10][7]);

        Drawable myDrawable12 = getDrawable(getResources().getIdentifier(currstory[11][arrayB[12]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage12 = ((BitmapDrawable) myDrawable12).getBitmap();
        text1_12.setText(currstory[11][6]);
        pic12.setImageBitmap(anImage12);
        text2_12.setText(currstory[11][7]);
        text3_12.setText(currstory[11][8]);

        Drawable myDrawable13 = getDrawable(getResources().getIdentifier(currstory[12][arrayB[13]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage13 = ((BitmapDrawable) myDrawable13).getBitmap();
        text1_13.setText(currstory[12][6]);
        pic13.setImageBitmap(anImage13);
        text2_13.setText(currstory[12][7]);

        Drawable myDrawable14 = getDrawable(getResources().getIdentifier(currstory[13][arrayB[14]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage14 = ((BitmapDrawable) myDrawable14).getBitmap();
        text1_14.setText(currstory[13][6]);
        pic14.setImageBitmap(anImage14);
        text2_14.setText(currstory[13][7]);

        Drawable myDrawable15 = getDrawable(getResources().getIdentifier(currstory[14][arrayB[15]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage15 = ((BitmapDrawable) myDrawable15).getBitmap();
        text1_15.setText(currstory[14][6]);
        pic15.setImageBitmap(anImage15);
        text2_15.setText(currstory[14][7]);






////        try {
////            Drawable myDrawable5 = getDrawable(getResources().getIdentifier(currstory[4][arrayB[4]], "drawable", "ca.utoronto.ece1778.tripstory"));
////            Bitmap anImage5 = ((BitmapDrawable) myDrawable5).getBitmap();
////            text1_5.setText(currstory[4][6]);
////            pic5.setImageBitmap(anImage5);
////            text2_5.setText(currstory[4][7]);
////        }
////        finally {
////            Toast.makeText(Compile.this,"No luck", Toast.LENGTH_SHORT).show();
////        }
////        String listString = "";
////
//
//
////        for (int i = 0; i<currstory.length; i++){
////            text1.setText(currstory[i][6]);
////            text2.setText(currstory[i][7]);
////        }
//
//


        //back = (Button)findViewById(R.id.compile_back);
        //back.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        Intent intent = new Intent(Compile.this, MainActivity.class);
        //        startActivity(intent);
        //    }
        //});

    }

    private void takeScreenShot()
    {
        View u = this.findViewById(R.id.scroll);

        ScrollView z = (ScrollView) this.findViewById(R.id.scroll);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u,totalHeight,totalWidth);

        //Save bitmap
        String extr = Environment.getExternalStorageDirectory()+"/DCIM/Camera/";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = timeStamp + "_Story.jpg";
        myPath = new File(extr, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            MediaStore.Images.Media.insertImage(this.getContentResolver(), b, "Screen", "screen");
        }catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compiler_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.save) {
            Toast.makeText(this, " Story Saved! " ,
                    Toast.LENGTH_LONG).show();
            System.out.println( " Capturing screenshot now!" );
            takeScreenShot();
            uploadImages(myPath.getAbsolutePath());
            return true;
        /*} else if (id == R.id.share) {
            Animation bottomUp = AnimationUtils.loadAnimation(this,
                    R.anim.bottom_up);
            ViewGroup hiddenPanel = (ViewGroup)findViewById(R.id.hidden_panel);
            hiddenPanel.startAnimation(bottomUp);
            hiddenPanel.setVisibility(View.VISIBLE);
            */
        } else if (id == R.id.back) {
            Intent intent = new Intent(Compile.this, MainMenuActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void uploadImages (String filename) {
        // File or Blob
        File file_temp = new File(filename);
        Uri file = Uri.fromFile(file_temp);

        // Once we have a filename, the filename is set as a shared preference and used throughout.
        SharedPreferences sharedPref = this.getSharedPreferences("ca.utoronto.ece1778.tripstory", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("filename_uri", filename);
        editor.commit();

        // Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        // Upload file and metadata to the path 'images/mountains.jpg'
        uploadTask = storageRef.child("images/"+file.getLastPathSegment()).putFile(file, metadata);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                System.out.println("Upload is " + (int) progress + "% done");
                Toast.makeText(getApplication(), ("Upload is " + (int) progress + "% done"),
                        Toast.LENGTH_LONG).show();
                if (progress == 100) {
                    Toast.makeText(getApplication(), " File Upload Successful ",
                            Toast.LENGTH_LONG).show();
                }
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Handle successful uploads on complete
                Uri downloadUrl = taskSnapshot.getMetadata().getDownloadUrl();
            }
        });

    }


}
