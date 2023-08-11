package com.example.mynews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImageTranscoderType;
import com.facebook.imagepipeline.core.MemoryChunkType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final String BASE_URL = "https://newsapi.org/v2/";
    private final String api_key = "739904b4b49c4cd1b425a7dc29361092";

    private RecyclerView rcDataView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize( getApplicationContext(),
                ImagePipelineConfig.newBuilder(getApplicationContext())
                        .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                        .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                        .experiment().setNativeCodeDisabled(true)
                        .build());

        rcDataView =  this.findViewById(R.id.rc_news_view);
        rcDataView.setHasFixedSize(true);   // contents의 크기가 물리적으로 변해도 RecyclerView의 크기를 고정시킴
        layoutManager = new LinearLayoutManager(this);
        rcDataView.setLayoutManager(layoutManager);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NewsApi newsApi = retrofit.create(NewsApi.class);
        Call<NewsResponse> call = newsApi.getNewsData("kr", api_key);
        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if ( response.isSuccessful() ) {
                    NewsResponse newsResponse = response.body();
                    if ( newsResponse!=null) {
                        if ( newsResponse.getStatus().toLowerCase().equals("ok")) {
                            adapter = new NewsAdaptor(newsResponse.getArticles());
                            rcDataView.setAdapter(adapter);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.e("newsApi 결과", "newsApi.getNewsData() 실패 : " + t.getMessage());
            }
        });
    }
}