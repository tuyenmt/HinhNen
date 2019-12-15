package com.macbook.hinhnen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.macbook.hinhnen.adapter.AdapterListenner;
import com.macbook.hinhnen.adapter.ViewAdapter;
import com.macbook.hinhnen.model.Photo;
import com.macbook.hinhnen.model.Wallpaper;
import com.macbook.hinhnen.retrofit.Retrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<Photo> photos;
    private ViewAdapter adapter;
    private SwipeRefreshLayout swipeLayout;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        swipeLayout = findViewById(R.id.swipeRefreshLayout);

        photos = new ArrayList<>();

        adapter = new ViewAdapter(MainActivity.this, photos, new AdapterListenner() {
            @Override
            public void onClick(int p) {
                Intent intent = new Intent(MainActivity.this,Detai_Img.class);
                intent.putExtra("url",photos.get(p).getUrlL());
                startActivity(intent);
                Log.d("imageeee",photos.get(p).getUrlS());
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        getData(page);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MainActivity.this.page = 1;
                photos.clear();
                getData(MainActivity.this.page);
            }
        });
    }
    private void getData(int page) {
        AndroidNetworking.post("https://www.flickr.com/services/rest/")
                .addBodyParameter("method", "flickr.favorites.getList")
                .addBodyParameter("api_key", "f7a259ccf01293370e8cd8d754cb6aa4")
                .addBodyParameter("user_id", "184865006@N08")
                .addBodyParameter("format", "json")
                .addBodyParameter("nojsoncallback", "1")
                .addBodyParameter("extras", "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o")
                .addBodyParameter("per_page", "25")
                .addBodyParameter("page", String.valueOf(page))
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Wallpaper.class, new ParsedRequestListener() {
                    @Override
                    public void onResponse(Object response) {
                        swipeLayout.setRefreshing(false);
                        Wallpaper hd = (Wallpaper) response;
                        List<Photo> photos = hd.getPhotos().getPhoto();
                        MainActivity.this.photos.addAll(photos);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

}
