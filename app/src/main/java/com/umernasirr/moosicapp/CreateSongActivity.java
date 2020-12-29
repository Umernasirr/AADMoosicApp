package com.umernasirr.moosicapp;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.security.Permission;
import java.util.ArrayList;

public class CreateSongActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    private static final int FILE_SELECT_CODE = 69;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsong);


        Button btnSelectFile = (Button) findViewById(R.id.btnSelectFile);
        Button btnUploadFile = (Button) findViewById(R.id.btnUploadFile);


        View.OnClickListener onClickListenerSelect = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkPermission()) {
                        filePicker();
                    } else {
                        requestPermission();
                    }
                } else {
                    filePicker();
                }
            }
        };

        btnSelectFile.setOnClickListener(onClickListenerSelect);

    }


    public void filePicker() {
        Toast.makeText(this, "File Picker Call", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setMaxSelection(1)
                .enableImageCapture(true)
                .setSingleChoiceMode(true)
                .setSkipZeroSizeFiles(true)
                .setShowImages(true)
                .setShowVideos(false)
                .setShowAudios(false)
                .setSuffixes("jpg")
                .build());
        startActivityForResult(intent, FILE_SELECT_CODE);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CreateSongActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(CreateSongActivity.this, "Please Given Permission to Upload File", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(CreateSongActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    private boolean checkPermission() {
        int results = ContextCompat.checkSelfPermission(CreateSongActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (results == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(CreateSongActivity.this, "Permission Successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CreateSongActivity.this, "Permission Failed", Toast.LENGTH_SHORT).show();

                }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case FILE_SELECT_CODE:
                ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
                ArrayList<MediaFile> filteredFiles = new ArrayList<>();
                //Do something with files
                for(MediaFile file: files){
                    Log.d("myTag", file.getName());
                    Log.d("myTag", file.getPath()+ "");
//


//                    if(file.getMediaType() == MediaFile.TYPE_VIDEO ){
//                        filteredFiles.add(file);
//                    }
                }
                break;
        }
    }

    public String getRealFromUri(Uri uri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            return uri.getPath();
        } else {
            cursor.moveToFirst();
            int id = cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID);
            return cursor.getString(id);


        }
    }

}
