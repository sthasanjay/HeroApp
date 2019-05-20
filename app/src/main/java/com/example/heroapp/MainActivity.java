package com.example.heroapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.heroapp.HeroesApi.HeroesAPI;
import com.example.heroapp.Model.Heroes;
import com.example.heroapp.Model.ImageResponse;
import com.example.heroapp.Url.Url;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText etName,etDes;
    private Button btnSave;
    private ImageView imgProfile;
    private String imagePath,imageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=findViewById(R.id.etName);
        etDes =findViewById(R.id.etDes);
        btnSave =findViewById(R.id.btnSave);
        imgProfile =findViewById(R.id.imgProfile);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BroewsImage();

            }
        });
    }

    private void BroewsImage() {

        Intent intent =new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,0);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if(data ==null){
                Toast.makeText(this,"Please Select an Image", Toast.LENGTH_LONG).show();
            }
        }
        Uri uri=data.getData();
        imagePath=getRealPathFromUri(uri);
        previewImage(imagePath);
    }

    private String getRealPathFromUri(Uri uri){

        String[] projection ={MediaStore.Images.Media.DATA};
        CursorLoader loader= new CursorLoader(getApplicationContext(),uri,projection, null,null,null);
        Cursor cursor= loader.loadInBackground();
        int colIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result =cursor.getString(colIndex);
        cursor.close();
        return result;
    }

    private void previewImage(String imagePath){

        File imgfile= new File(imagePath);
        if(imgfile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            imgProfile.setImageBitmap(myBitmap);
        }

    }

    private void StictMode(){

        StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }


    private void SaveImageOnly(){

        File file =new File(imagePath);

        RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/from-data"),file);
        MultipartBody.Part body= MultipartBody.Part.createFormData("imageFile",file.getName(),requestBody);

        HeroesAPI heroesAPI= Url.getInstance().create(HeroesAPI.class);
        Call<ImageResponse> responseCall= heroesAPI.uploadImage(body);
        StictMode();

        try {
            Response<ImageResponse> imageResponseResponse=responseCall.execute();
            imageName=imageResponseResponse.body().getFilename();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





    private void Save() {
        String name=etName.getText().toString();
        String desc=etDes.getText().toString();
        SaveImageOnly();
        HeroesAPI heroesAPI= Url.getInstance().create(HeroesAPI.class);

        Call<Void> heroesCall= heroesAPI.addHero(name,desc,imageName);

        heroesCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Code "+response.code(),Toast.LENGTH_LONG ).show();
                }
                Toast.makeText(MainActivity.this,"Sucesfully Added ",Toast.LENGTH_LONG ).show();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Error: "+t.getLocalizedMessage(),Toast.LENGTH_LONG ).show();

            }
        });

    }
}
