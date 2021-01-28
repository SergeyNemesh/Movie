package com.example.mymovie.Call;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public ApiClient() {}

    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    public static ApiService getAPIService() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiService.class);
    }
}
