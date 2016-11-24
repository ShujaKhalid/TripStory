package ca.utoronto.ece1778.tripstory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class MainMenuActivity extends AppCompatActivity {

    int MyVersion = Build.VERSION.SDK_INT;
    String mCurrentPhotoPath;
    File storageDirExtract;
    public File photoFile;
    // Create a storage reference from our app
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://tripstory-47cf0.appspot.com");
    private UploadTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        System.out.println("MyVersion = " + MyVersion);
        System.out.println("Build.VERSION_CODES.LOLLIPOP_MR1 = " + Build.VERSION_CODES.LOLLIPOP_MR1);

        // Request Permissions
        if (MyVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            if (!checkIfAlreadyhavePermission()) {
                System.out.println("Requesting Permission = ");
                requestForSpecificPermission();
            }
        }

        // Set the layout
        setContentView(R.layout.activity_mainmenu);

        // OnClick listener for buttons on main menu

        Button BeginButton = (Button) findViewById(R.id.button);
        BeginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("BeginButton !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " );

                // Create intent to launch Main Activity from here
                Intent intent = new Intent(MainMenuActivity.this, ThemeSelectorActivity.class);

                //Start theme selector activity
                startActivity(intent);

            }
        });

        Button PersonButton = (Button) findViewById(R.id.button2);
        PersonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Launch the front facing using a button
                dispatchTakePictureIntent();
                Snackbar.make(view, "Camera Launched", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

        Button ViewButton = (Button) findViewById(R.id.button3);
        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                System.out.println("ViewButton !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! " );

                // Create intent to launch Main Activity from here
                Intent intent = new Intent(MainMenuActivity.this, ListActivity.class);

                //Start theme selector activity
                startActivity(intent);

            }
        });

    }

    // ======================================================================
    // FUNCTIONS FOR PERMISSION CHECK
    // ======================================================================

    private boolean checkIfAlreadyhavePermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestForSpecificPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    // ======================================================================
    // PICTURE INTENT BEGINS HERE
    // ======================================================================
    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        //TODO camera stuff.
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            photoFile = createImageFile();

            // Once we have a filename, the filename is set as a shared preference and used throughout.
            SharedPreferences sharedPref = this.getSharedPreferences("ca.utoronto.ece1778.tripstory", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("personaluser", photoFile.getAbsolutePath());
            editor.commit();

        } catch (IOException ex) {
            System.err.println("photoFile not created =(");
            System.err.println(ex);
        }

        Uri photoURI = FileProvider.getUriForFile(this,
                "ca.utoronto.ece1778.tripstory.fileprovider",
                photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        takePictureIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT);
        startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

    }

    public File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        storageDirExtract = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String imageFileName = timeStamp;
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");

        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file : " + image.getAbsolutePath();

        return image;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Upload the image to FireBase
        //uploadImages(photoFile.getAbsolutePath());
        //TODO handle result
        if (requestCode == REQUEST_TAKE_PHOTO) {
            if (resultCode == RESULT_OK) {


            } else if (resultCode == RESULT_CANCELED) {
                //photoFile.delete();
                finish();
            } else {
                // Image capture failed, advise user
            }
        }

    }



}

