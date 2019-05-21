package com.example.heroapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.heroapp.HeroesApi.HeroesAPI;
import com.example.heroapp.Model.Heroes;
import com.example.heroapp.Url.Url;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Display_hero extends AppCompatActivity {
    private RecyclerView heorDisplay;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hero);
        heorDisplay=findViewById(R.id.heorDisplay);
        btnBack= findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Display_hero.this,MainActivity.class);
                startActivity(intent);
            }
        });

        heorDisplay.setLayoutManager(new LinearLayoutManager(this));
        HeroesAPI heroesApi = Url.getInstance().create(HeroesAPI.class);
        Call<List<Heroes>> listCall = heroesApi.getHeroes();
        listCall.enqueue(new Callback<List<Heroes>>() {
            @Override
            public void onResponse(Call<List<Heroes>> call, Response<List<Heroes>> response) {
                List<Heroes> heroes = response.body();
                HeroesAdapter heroesAdapter= new HeroesAdapter(Display_hero.this,heroes);
                heorDisplay.setAdapter(heroesAdapter);
                heorDisplay.setLayoutManager(new LinearLayoutManager(Display_hero.this));
            }

            @Override
            public void onFailure(Call<List<Heroes>> call, Throwable t) {

            }
        });

    }
}
