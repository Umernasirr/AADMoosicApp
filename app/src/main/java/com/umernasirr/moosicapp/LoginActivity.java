package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);

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