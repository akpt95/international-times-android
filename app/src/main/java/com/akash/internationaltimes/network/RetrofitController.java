package com.akash.internationaltimes.network;

import android.util.Log;

import com.akash.internationaltimes.BuildConfig;
import com.akash.internationaltimes.network.api.NewsApi;
import com.akash.internationaltimes.utils.Constants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitController {
    private static final String TAG = RetrofitController.class.getSimpleName();

    private static RetrofitController retrofitControllerInstance;
    private static final int OKHTTP_CLIENT_TIMEOUT = 30;
    private final static String ACCEPT = "application/json";
    private final static String CONTENT_TYPE = "application/json";

    private NewsApi newsApi;


    public static synchronized RetrofitController getInstance() {
        Log.v(TAG, RetrofitController.class.getName() + " in getInstance()");

        if (retrofitControllerInstance == null) {
            retrofitControllerInstance = new RetrofitController();
        }

        return retrofitControllerInstance;
    }


    @NotNull
    private Retrofit getRetrofitBuilder() {
        OkHttpClient okHttpClient;
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        HeaderInterceptor headerInterceptor = new HeaderInterceptor();

        okHttpClient = new OkHttpClient.Builder().
                connectTimeout(OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(OKHTTP_CLIENT_TIMEOUT, TimeUnit.SECONDS)
                .followRedirects(false)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build();

        return new Retrofit.Builder().client(okHttpClient).baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public NewsApi getNewsApi(){

        if (newsApi!=null)
            return newsApi;

        Retrofit retrofit = getRetrofitBuilder();
        newsApi = retrofit.create(NewsApi.class);

        return newsApi;
    }


    public class HeaderInterceptor implements Interceptor {

        @NotNull
        @Override
        public Response intercept(@NotNull Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder requestBuilder;
            requestBuilder = request.newBuilder().addHeader(Constants.ACCEPT_KEY,ACCEPT)
                    .addHeader(Constants.CONTENT_TYPE_KEY,CONTENT_TYPE)
                    .addHeader(Constants.AUTHORIZATION_KEY, Constants.BEARER+ BuildConfig.API_KEY);


            Request newRequest = requestBuilder.build();
            return chain.proceed(newRequest);
        }
    }

}
