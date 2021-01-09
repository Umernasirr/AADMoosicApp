package com.umernasirr.moosicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PostViewActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtViewPostTitle, txtViewPostCreatedAt, txtViewPostURL, txtViewPostDescription;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postview);




        Toolbar toolbar = findViewById(R.id.postViewToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutPostView);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        NavigationView navViewListings = findViewById(R.id.navViewPostView);

        navViewListings.setNavigationItemSelectedListener(this);




        txtViewPostTitle =  (TextView) findViewById(R.id.txtViewPostTitle);
        txtViewPostCreatedAt =  (TextView) findViewById(R.id.txtViewPostCreatedAt);
        txtViewPostURL =  (TextView) findViewById(R.id.txtViewPostURL);
        txtViewPostDescription =  (TextView) findViewById(R.id.txtViewPostDescription);

        Button btnGoBackPost = (Button) findViewById(R.id.btnGoBackPost);
        Bundle bundle = getIntent().getExtras();

        String title = bundle.getString("title");
        String description = bundle.getString("description");
        String url = bundle.getString("url");
        String createdAt = bundle.getString("createdAt");

        txtViewPostTitle.setText(title);
        txtViewPostCreatedAt.setText(createdAt);
        txtViewPostURL.setText(url);
        txtViewPostDescription.setText(description);


        btnGoBackPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PostListActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
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
