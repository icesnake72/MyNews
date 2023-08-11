package com.example.mynews;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("top-headlines")
    Call<NewsResponse> getNewsData(
            @Query("country") String countryCode,   // 국가 코드 : kr이 들어갈 예정
            @Query("apiKey") String api_key         // News API Service에서 발급받은 api key
    );
}
