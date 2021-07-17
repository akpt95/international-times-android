package com.akash.internationaltimes.network.api;

import com.akash.internationaltimes.network.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("/v2/top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("country") String country,
                                       @Query("category") String category,
                                       @Query("q") String searchString,
                                       @Query("page") int page);

}
