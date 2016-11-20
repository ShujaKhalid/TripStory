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
    //commented out//FirebaseStorage storage = FirebaseStorage.getInstance();
    //commented out//StorageReference storageRef = storage.getReferenceFromUrl("gs://tripstory-47cf0.appspot.com");
    private UploadTask uploadTask;
    private TextView story;
    private Button back;
    private Context mContext;
    public File myPath;

    private TextView text1, text2,
            text1_2, text2_2,
            text1_3, text2_3,
            text1_4, text2_4,
            text1_5, text2_5;

    private int[] arrayB;
    private int buddy;
    private ImageView pic1, pic2, pic3, pic4, pic5;

    public String[][] currstory = new String[][]{
            {"f1", "Who do you want to pick as a friend?", "f1o1", "f1o2", "f1o3", "f1o4", "You pick", "as a friend"},
            {"f2", "You and your friend find what?", "f2o1", "f2o2", "f2o3", "f2o4", "You and your friend find", ""},
            {"f3", "Suddenly, you see a _________ coming from behind", "f3o1", "f3o2", "f3o3", "f3o4", "Suddenly, you see a", "coming from behind."},
            {"f4", "Where do you go to hide away from it?", "f4o1", "f4o2", "f4o3", "f4o4", "So you hide away in a", "from it"},
            {"f5", "There you find _________ stuck in a net", "f5o1", "f5o2", "f5o3", "f5o4", "There you find a", "stuck in a net"}
//
//            {"frame1", "Who do you want to pick as a friend?", "f1_o1", "f1_o2", "f1_o3", "f1_o4", "You picked ", " as a friend"},
//            {"frame2", "You and your friend find what?", "f2_o1", "f2_o2", "f2_o3", "f2_o4", "You and your friend found ", " "},
//            {"frame3", "Suddenly, you see a _________ coming from behind", "f3_o1", "f3_o2", "f3_o3", "f3_o4", "Suddenly, you saw a ", " coming from behind."},
//            {"frame4", "Where do you go to hide away from it?", "f4_o1", "f4_o2", "f4_o3", "f4_o4", "So you hid away in a ", " from it"},
//            {"frame5", "There you find _________ stuck in a net", "f5_o1", "f5_o2", "f5_o3", "f5_o4", "There you found a ", " stuck in a net"}
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
        text1_4 = (TextView) findViewById(R.id.text1_4);
        text2_4 = (TextView) findViewById(R.id.text2_4);
        text1_5 = (TextView) findViewById(R.id.text1_5);
        text2_5 = (TextView) findViewById(R.id.text2_5);

        pic1 = (ImageView) findViewById(R.id.pic1);
        pic2 = (ImageView) findViewById(R.id.pic2);
        pic3 = (ImageView) findViewById(R.id.pic3);
        pic4 = (ImageView) findViewById(R.id.pic4);
        pic5 = (ImageView) findViewById(R.id.pic5);

//        Bundle extras = getIntent().getExtras();
//        int[] arrayB = extras.getIntArray("myStoryint");



//        Bundle b=this.getIntent().getExtras();
//        String[] array=b.getStringArray(key);

        Drawable myDrawable1 = getDrawable(getResources().getIdentifier(currstory[0][buddy],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage1      = ((BitmapDrawable) myDrawable1).getBitmap();
        text1.setText(currstory[0][6]);
        pic1.setImageBitmap(anImage1);
        text2.setText(currstory[0][7]);

        Drawable myDrawable2 = getDrawable(getResources().getIdentifier(currstory[1][arrayB[1]+2],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage2      = ((BitmapDrawable) myDrawable2).getBitmap();
        text1_2.setText(currstory[1][6]);
        pic2.setImageBitmap(anImage2);
        text2_2.setText(currstory[1][7]);

        Drawable myDrawable3 = getDrawable(getResources().getIdentifier(currstory[2][arrayB[2]+2],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage3      = ((BitmapDrawable) myDrawable3).getBitmap();
        text1_3.setText(currstory[2][6]);
        pic3.setImageBitmap(anImage3);
        text2_3.setText(currstory[2][7]);

        Drawable myDrawable4 = getDrawable(getResources().getIdentifier(currstory[3][arrayB[3]+2],"drawable","ca.utoronto.ece1778.tripstory"));
        Bitmap anImage4      = ((BitmapDrawable) myDrawable4).getBitmap();
        text1_4.setText(currstory[3][6]);
        pic4.setImageBitmap(anImage4);
        text2_4.setText(currstory[3][7]);

        Drawable myDrawable5 = getDrawable(getResources().getIdentifier(currstory[4][arrayB[4]+2], "drawable", "ca.utoronto.ece1778.tripstory"));
        Bitmap anImage5 = ((BitmapDrawable) myDrawable5).getBitmap();
        text1_5.setText(currstory[4][6]);
        pic5.setImageBitmap(anImage5);
        text2_5.setText(currstory[4][7]);







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

        // Create the file metadata
        StorageMetadata metadata = new StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build();

        // Upload file and metadata to the path 'images/mountains.jpg'
        // NOV 18 commentedout//uploadTask = storageRef.child("images/"+file.getLastPathSegment()).putFile(file, metadata);

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
