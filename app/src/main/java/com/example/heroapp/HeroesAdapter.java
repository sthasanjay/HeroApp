package com.example.heroapp;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.heroapp.Model.Heroes;
import com.example.heroapp.Url.Url;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

class HeroesAdapter extends RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder> {
    Context mContext;
    List<Heroes> heroesList;

    public HeroesAdapter(Context mContext, List<Heroes> heroesList) {
        this.mContext = mContext;
        this.heroesList = heroesList;
    }

    @NonNull
    @Override
    public HeroesAdapter.HeroesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.view_heroes, viewGroup, false);
        return new HeroesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroesAdapter.HeroesViewHolder heroesViewHolder, int i) {

        final Heroes heroes = heroesList.get(i);
        String imgpath = Url.BASE_URL + "uploads/"+ heroes.getImageName();
        StrictMode();

        try{
            URL url = new URL(imgpath);
            heroesViewHolder.image.setImageBitmap(BitmapFactory.decodeStream((InputStream) url.getContent()));
        }catch (IOException e) {
            e.printStackTrace();
        }
        heroesViewHolder.tvName.setText(heroes.getName());
        heroesViewHolder.tvDesc.setText(heroes.getDesc());




    }

    @Override
    public int getItemCount() {
        return heroesList.size();
    }

    public class HeroesViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView tvName, tvDesc;

        public HeroesViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.imgview);
            tvName = itemView.findViewById(R.id.tvName);
            tvDesc = itemView.findViewById(R.id.tvDesc);

        }
    }
    private void StrictMode(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
}
