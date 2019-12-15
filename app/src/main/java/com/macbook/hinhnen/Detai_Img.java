package com.macbook.hinhnen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.macbook.hinhnen.model.Photo;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class Detai_Img extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1000;
    Context context;
    private ImageView img;
    private FloatingActionButton size1, size2, size3;
    DownloadManager downloadManager;
    List<Photo> photos;
    ViewPager mPager;


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mPager =  findViewById(R.id.pager);
        setContentView(R.layout.activity_detai__img);
        img = findViewById(R.id.img);
        size1 = findViewById(R.id.size1);
        size2 = findViewById(R.id.size2);
        size3 = findViewById(R.id.size3);
        Glide.with(this)
                .load(getIntent()
                        .getStringExtra("url"))
                .into(img);

        size1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                downloadManager = (DownloadManager) getSystemService(context.DOWNLOAD_SERVICE);
                Uri uri = Uri.parse(getIntent()
                        .getStringExtra("url"));
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Picasso.get()
                        .load(getIntent()
                                .getStringExtra("url")).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        try {
                            File mydie = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/HDWallpaper");
                            if (!mydie.exists()) {
                                mydie.mkdirs();
                            }
                            FileOutputStream fileOutputStream = new FileOutputStream(new File(mydie, new Date().toString() + ".jpg"));
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
                            fileOutputStream.flush();
                            fileOutputStream.close();
                            Toast.makeText(context, "OK", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        });




        Log.d("imgggggggg",getIntent().getStringExtra("url"));
    }

    public void back(View view) {
        Intent intent = new Intent(Detai_Img.this, MainActivity.class);
        startActivity(intent);
    }


}
