package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private  AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

        Retrofit  retrofit = new Retrofit.Builder()
                .baseUrl("https://moosikk.herokuapp.com/api")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authApi = retrofit.create(AuthApi.class);
        

// This is our registration clicker
        View.OnClickListener onClickListenerRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);

            }
        };
// This will handle login
        View.OnClickListener onClickListenerLogin = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };


        btnRegister.setOnClickListener(onClickListenerRegister);


    }


}