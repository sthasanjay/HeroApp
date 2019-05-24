package com.example.heroapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heroapp.HeroesApi.HeroesAPI;
import com.example.heroapp.Model.LoginSignupResponse;
import com.example.heroapp.Url.Url;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
     private EditText etusername, etpassword;
     private Button btnLogin, btnRegister;
    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        etusername = findViewById(R.id.etUsername);
        etpassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        
        
        
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              checkUser();
            }
        });
        
        
        
        
        
    }

    private void checkUser() {

        HeroesAPI heroesAPI = Url.getInstance().create(HeroesAPI.class);

        String username = etusername.getText().toString().trim();
        String password = etpassword.getText().toString().trim();

        Call<LoginSignupResponse>usersCall = heroesAPI.checkUser(username, password);

        usersCall.enqueue(new Callback<LoginSignupResponse>() {
            @Override
            public void onResponse(Call<LoginSignupResponse> call, Response<LoginSignupResponse> response) {

                if (!response.isSuccessful()){


                    Toast.makeText(LoginActivity.this,"Either username or password is incorrect", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {

                    if (response.body().getSucess()){

                      Url.Cookie = response.headers().get("Set-Cookie");
                      Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                      startActivity(intent);
                      finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginSignupResponse> call, Throwable t) {


                Toast.makeText(LoginActivity.this, "error"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
