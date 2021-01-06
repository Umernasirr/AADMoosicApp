package com.umernasirr.moosicapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        SharedPreferences.Editor editor = pref.edit();

        if(pref.getBoolean("isAuthenticated", false)){
                //uncomment thi after the reg api is done
            startActivity(new Intent(getApplicationContext(), SongsListActivity.class));
        }

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        EditText email = (EditText) findViewById(R.id.edtTxtUser);
        EditText password = (EditText) findViewById(R.id.edtTxtPass);
        Retrofit retrofit = RetrofitFactory.getRetrofit();
        TextView error = (TextView)findViewById((R.id.txtError));

        spinner = (ProgressBar)findViewById(R.id.loaderMusic);
        spinner.setVisibility(View.GONE);

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);



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
                if(email.getText().toString().equals("")){
                    error.setText("Please enter your email");
                }
                else if(password.getText().toString().equals("")){
                    error.setText("Please enter your password");
                }else{
                    spinner.setVisibility(View.VISIBLE);
                    UserModel userModel = new UserModel(email.getText().toString(),password.getText().toString());
                    Call<AuthResponse> call = apiInterface.login(userModel);


                    call.enqueue(new Callback<AuthResponse>() {
                        @Override
                        public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                            spinner.setVisibility(View.GONE);

                            AuthResponse result = response.body();



                            if(String.valueOf(response.code()).equals("200")) {
                                Toast.makeText(getApplicationContext(),"Successfully logged in", Toast.LENGTH_SHORT).show();

                                editor.putString("token", result.getToken());
                                editor.putBoolean("isAuthenticated",true);
                                Gson gson = new Gson();
                                String json =  gson.toJson(result.getUser());
//                                gson.toString()
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
        btnLogin.setOnClickListener(onClickListenerLogin);

    }



}