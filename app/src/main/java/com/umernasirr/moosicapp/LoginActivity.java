package com.umernasirr.moosicapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class LoginActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutMain);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        NavigationView navView = findViewById(R.id.navView);

        navView.setNavigationItemSelectedListener(this);


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
                Intent intent = new Intent(getApplicationContext(), SongsListActivity.class);
                startActivity(intent);
            }
        };

        btnRegister.setOnClickListener(onClickListenerRegister);
        btnLogin.setOnClickListener(onClickListenerLogin);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.mitemLogin:
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemSignup:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;


            case R.id.mitemForgot:
//                intent = new Intent(this, CreateSongActivity.class);
//                startActivity(intent);
                break;

        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}