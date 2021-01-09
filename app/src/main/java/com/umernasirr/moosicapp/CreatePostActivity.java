package com.umernasirr.moosicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CreatePostActivity extends AppCompatActivity {


    EditText edtTxtPostName;
    EditText edtTxtPostDescription;
    EditText edtTxtPostURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);

        TextView error = (TextView) findViewById(R.id.txtErrorPlaylist);
        ProgressBar spinner = (ProgressBar) findViewById(R.id.loadPlaylist);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        spinner.setVisibility(View.GONE);


        Button btnGoback = (Button) findViewById(R.id.btnGoback);
        Button btnSavePost = (Button) findViewById(R.id.btnSavePost);

        edtTxtPostName = (EditText) findViewById(R.id.edtTxtPostName);
        edtTxtPostDescription = (EditText) findViewById(R.id.edtTxtPostDescription);
        edtTxtPostURL = (EditText) findViewById(R.id.edtTxtPostURL);


        btnGoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostListActivity.class);
                startActivity(intent);
            }
        });

        btnSavePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);

                String postTitle = edtTxtPostName.getText().toString();
                String post_url = edtTxtPostURL.getText().toString();
                String postDescription = edtTxtPostDescription.getText().toString();



                Log.d("myTag", "did i get here 1");
                if (!postTitle.equals("") && !post_url.equals("") && !postDescription.equals("")) {
                    Log.d("myTag", "did i get here 2");


                    SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                    SharedPreferences.Editor editor = pref.edit();
                    Gson gson = new Gson();

                    AuthResponse authResponse = gson.fromJson(pref.getString("user", ""), AuthResponse.class);


                    PostAdd postModel = new PostAdd(postTitle, postDescription, authResponse.getUser().get_id(), post_url);





                    Call<PostAdd> call = apiInterface.addPost(postModel);
                    Log.d("myTag", "did i get here 3");

                    call.enqueue(new Callback<PostAdd>() {
                        @Override
                        public void onResponse(Call<PostAdd> call, Response<PostAdd> response) {
                            spinner.setVisibility(View.GONE);

                            PostAdd result = response.body();

                            if (String.valueOf(response.code()).equals("200")) {
                                Toast.makeText(getApplicationContext(), "Successfully Created Post", Toast.LENGTH_SHORT).show();

                            } else {
                                Object message = new Gson().fromJson(response.errorBody().charStream(), PostResponse.class);
                                Log.d("myTag", message.toString());


                               Log.d("myTag",new Gson().toJson(response.errorBody()) ) ;

//                                error.setText(message.getError());
                            }
                        }

                        @Override
                        public void onFailure(Call<PostAdd> call, Throwable t) {
                            spinner.setVisibility(View.GONE);
                            Log.d("myTag", t.getMessage());
                        }
                    });
                }
            }
        });
    }
}

