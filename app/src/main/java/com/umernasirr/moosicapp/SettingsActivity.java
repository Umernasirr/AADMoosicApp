package com.umernasirr.moosicapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class SettingsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    AuthResponse authResponse ;
    Button btnSaveSettings;
    EditText editTextUsername;
    EditText editTextPassword ;
    EditText editTxtEmail ;
    EditText editTxtGender;

    TextView txtViewHeader ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        btnSaveSettings = (Button) findViewById(R.id.btnSaveSettings);


        if(!pref.getString("user", "").equals("") && pref.getString("user", "") != null ) {
            Gson gson = new Gson();

            authResponse =  gson.fromJson( pref.getString("user", ""), AuthResponse.class);


            txtViewHeader = (TextView) findViewById(R.id.textView7);

            txtViewHeader.setText("Hey " + authResponse.getUser().getName() + " !" );
             editTextUsername = findViewById(R.id.editTextUsername);
             editTextPassword = findViewById(R.id.editTxtPassword);
             editTxtEmail = findViewById(R.id.editTxtEmail);
             editTxtGender = findViewById(R.id.editTxtGender);

            editTxtEmail.setText(authResponse.getUser().getEmail());
            editTextUsername.setText(authResponse.getUser().getName());
            editTextPassword.setText(authResponse.getUser().getPassword());

            editTxtGender.setText(authResponse.getUser().getGender());
            editTxtGender.setEnabled(false);
            editTxtEmail.setEnabled(false);


//            startActivity(new Intent(getApplicationContext(), SongsListActivity.class));
        }




        Toolbar toolbar = findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutSettings);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        NavigationView navViewSettings = findViewById(R.id.navViewSettings);

        navViewSettings.setNavigationItemSelectedListener(this);

        // Implementation


        btnSaveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editTextUsername = findViewById(R.id.editTextUsername);
                editTextPassword = findViewById(R.id.editTxtPassword);
                editTxtEmail = findViewById(R.id.editTxtEmail);
                editTxtGender = findViewById(R.id.editTxtGender);

                User user = new User();

                if(!editTextPassword.equals("") && !editTextUsername.equals("")){
                    user.setName(editTextUsername.getText().toString());
                    user.setPassword(editTextPassword.getText().toString());
                    user.setGender(authResponse.getUser().getGender());
                    user.setEmail(authResponse.getUser().getEmail());

                    authResponse.setUser(user);
                    Gson gson = new Gson();
                    String json =  gson.toJson(authResponse);
                    editor.putString("user", json);
                    editor.apply();




                }




            }
        });
    }

    public boolean onNavigationItemSelected (@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()) {
            case R.id.mitemProfile:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemListings:
                intent = new Intent(this, SongsListActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemUpload:
                intent = new Intent(this, CreateSongActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemPlaylist:
                intent = new Intent(this, PlaylistListActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemPosts:
                intent = new Intent(this, PostListActivity.class);
                startActivity(intent);
                break;

            case R.id.mitemLogout:

                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();

                editor.putString("token", "");
                editor.putBoolean("isAuthenticated",false);
                editor.putString("user", "");
                editor.commit();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);

                break;

        }
        return true;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
