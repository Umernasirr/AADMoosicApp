package com.umernasirr.moosicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    Button btnRegister;
    String selectedGender;
    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();


        Toolbar toolbar = findViewById(R.id.registerToolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayoutRegister);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openNavDrawer, R.string.closeNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        NavigationView navViewRegister = findViewById(R.id.navViewRegister);

        navViewRegister.setNavigationItemSelectedListener(this);
         btnRegister = (Button) findViewById(R.id.btnRegister1);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        TextView error = (TextView)findViewById((R.id.txtErrorSignUp));

        spinner = (ProgressBar)findViewById(R.id.loaderSignup);
        spinner.setVisibility(View.GONE);
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
//        EditText email = (EditText) findViewById(R.id.e);
        EditText name = (EditText) findViewById(R.id.editTextUsername);
        EditText password = (EditText) findViewById(R.id.editTextPassword);
        EditText email = (EditText) findViewById(R.id.editTextEmail);
        RadioButton femaleRadio = (RadioButton) findViewById(R.id.radio_female);
        RadioButton maleRadio = (RadioButton) findViewById(R.id.radio_male);
        TextView genderError = (TextView) findViewById(R.id.txtGenderError);
        TextView emailError = (TextView) findViewById(R.id.txtEmailError);
        TextView nameError = (TextView) findViewById(R.id.txtNameError);
        TextView passwordError = (TextView) findViewById(R.id.txtPasswordError);
        View.OnClickListener onClickListenerRegister = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(femaleRadio.isChecked()){
                    selectedGender = femaleRadio.getText().toString();
                    genderError.setText("");
                }
                else if(maleRadio.isChecked()){
                    selectedGender = maleRadio.getText().toString();
                    genderError.setText("");
                }
                else {
                    genderError.setText("Please select a gender");

                }
                if(TextUtils.isEmpty(email.getText())){
                    emailError.setText("Please type in the email");
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()){
                    emailError.setText("Please input the correct email format e.g. test@test.com");
                }else{
                    emailError.setText("");
                }

                if(TextUtils.isEmpty(name.getText())){
                    nameError.setText("Please enter your name");
                }else{
                    nameError.setText("");
                }
                if(password.length() < 6){
                    passwordError.setText("Please enter password of length more than 6");
                }
                else {
                    passwordError.setText("");
                }

                if(!TextUtils.isEmpty(selectedGender)
                        && password.length() >= 6
                        && !TextUtils.isEmpty(name.getText())
                        && Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()
                        && !TextUtils.isEmpty(email.getText())){
                    spinner.setVisibility(View.VISIBLE);
                    UserModel userModel = new UserModel(selectedGender,name.getText().toString(),email.getText().toString().toLowerCase(),password.getText().toString());
                    Call<AuthResponse> call = apiInterface.register(userModel);


                    call.enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            spinner.setVisibility(View.GONE);

                            AuthResponse result = response.body();



                            if(String.valueOf(response.code()).equals("200")) {
                                Toast.makeText(getApplicationContext(),"Successfully Registered", Toast.LENGTH_SHORT).show();

                                editor.putString("token", result.getToken());
                                editor.putBoolean("isAuthenticated",true);
                                Gson gson = new Gson();
                                String json =  gson.toJson(result.getUser());
                                editor.putString("user", json);
                                editor.commit();

                                startActivity(new Intent(getApplicationContext(), SongsListActivity.class));


                            }
                            else{
                                AuthResponse message =    new Gson().fromJson(response.errorBody().charStream(), AuthResponse.class);

                                error.setText(message.getError());


                            }



                        }

                        @Override
                        public void onFailure(Call<AuthResponse> call, Throwable t) {
                            spinner.setVisibility(View.GONE);

//                        AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
//                        builder1.setMessage("Write your message hedadassdasre.");
//                            Log.d("Login", "dasdasdasdasdadsdss");
//                            t.printStackTrace();
                            error.setText("Please check your internet connection");
                        }
                    });

                }



            }
        };
        btnRegister.setOnClickListener(onClickListenerRegister);

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


        }
        return true;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
