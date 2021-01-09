package com.umernasirr.moosicapp;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreateSongActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int REQUEST_GALLERY = 200;
    private static final int FILE_SELECT_CODE = 69;
    TextView txtSelectedFile;
    ImageView imgSelectedFile;
    EditText edtTxtSongName;
    public MediaFile selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createsong);


        Button btnSelectFile = (Button) findViewById(R.id.btnSelectFile);
        Button btnUploadFile = (Button) findViewById(R.id.btnUploadFile);
        Button btnGoBack = (Button) findViewById(R.id.btnGoBack);
        txtSelectedFile = findViewById(R.id.txtSelectedFile);
        imgSelectedFile = findViewById(R.id.imgSelectedFile);
        edtTxtSongName = findViewById(R.id.edtTxtSongName);





        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SongsListActivity.class);
                startActivity(intent);

            }
        });


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


        btnUploadFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selectedFile != null) {
                    Log.d("song", "Song Added");

                    Uri fileUri = selectedFile.getUri();
                    File file = new File(selectedFile.getPath());


                    RequestBody requestFile =
                            RequestBody.create(
                                    MediaType.parse(getContentResolver().getType(fileUri)),
                                    file
                            );

                    // MultipartBody.Part is used to send also the actual file name
                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    // add another part within the multipart request

                    String descriptionString = edtTxtSongName.getText().toString();




                    Log.d("song", file.getName());
                    Log.d("song", file.getAbsolutePath());
                    Log.d("song", file.toString());
                    try {
                        Log.d("song", body.body().contentLength() + "");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();


                    Gson gson = new Gson();
                    AuthResponse authResponse = gson.fromJson(pref.getString("user", ""), AuthResponse.class);

                    String userString = authResponse.getUser().get_id();


                    RequestBody _id =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, userString);


                    RequestBody description =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, descriptionString);

                    // finally, execute the request


                    Log.d("song", descriptionString);
                    Log.d("song", userString);
                    Log.d("song", _id.toString());
                    Log.d("song", description.toString());




                    Retrofit retrofit = RetrofitFactory.getRetrofit();
                    ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                    Call<ResponseBody> call = apiInterface.addSong(_id, description, body);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call,
                                               Response<ResponseBody> response) {




                            Toast.makeText(getApplicationContext(), "Song has been Uploaded to Application", Toast.LENGTH_SHORT).show();
                           String text =  gson.toJson(response.body());
                            Log.v("song",text.toString());


                            Log.v("song", "success");
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("song:", t.getMessage());
                        }
                    });
                }


            }

        });


    }





    public void filePicker() {
        Toast.makeText(this, "File Picker Call", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FilePickerActivity.class);
        intent.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setMaxSelection(1)
                .enableImageCapture(false)
                .setSingleChoiceMode(true)
                .setSkipZeroSizeFiles(true)
                .setShowImages(false)
                .setShowVideos(false)
                .setShowAudios(true)
                .setSuffixes(".mp3")
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


        Log.d("song", "DO i reach ");

        if (requestCode == FILE_SELECT_CODE) {
            Log.d("song", "DO i reach2 ");

            ArrayList<MediaFile> files = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);
            //Do something with files
            selectedFile = files.get(0);
            txtSelectedFile.setText(selectedFile.getName());
            imgSelectedFile.setImageURI(selectedFile.getThumbnail());
            edtTxtSongName.setText(selectedFile.getName());

        }
    }
}
