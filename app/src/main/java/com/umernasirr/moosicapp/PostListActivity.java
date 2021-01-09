package com.umernasirr.moosicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PostListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    public PostData postData;
    public RecyclerView rvPost;
    public PostListAdapter postListAdapter;
    public Button btnCreatePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        Toolbar toolbar = findViewById(R.id.postListToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutPost);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
        NavigationView navViewListings = findViewById(R.id.navViewPost);

        navViewListings.setNavigationItemSelectedListener(this);


        SearchView searchViewPost = (SearchView) findViewById(R.id.searchViewPost);


        Retrofit retrofit = RetrofitFactory.getRetrofit();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<PostResponse> call = apiInterface.getPosts();

        call.enqueue(new Callback<PostResponse>() {
            @Override
            public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                postData = new PostData();
                rvPost = findViewById(R.id.rvPost);

                PostResponse result = response.body();

                Gson gson = new Gson();
                String json = gson.toJson(response.body());
                Log.e("testing", json);


                for (int i = 0; i < result.getData().size(); i++) {
                    PostModel postModel = new PostModel(result.getData().get(i).get_id(), result.getData().get(i).getTitle(),
                            result.getData().get(i).getDescription(), result.getData().get(i).getUrl(), result.getData().get(i).getUser(), "12345", result.getData().get(i).getCreatedAt());

                    postData.postList.add(postModel);
                }
                postListAdapter = new PostListAdapter(PostListActivity.this, postData.postList);

                rvPost.setAdapter(postListAdapter);
                rvPost.setLayoutManager(new LinearLayoutManager(PostListActivity.this));
            }

            @Override
            public void onFailure(Call<PostResponse> call, Throwable t) {
                Log.d("myTag", "Error");
            }
        });


        searchViewPost.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                postListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        btnCreatePost = (Button) findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreatePostActivity.class);
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
