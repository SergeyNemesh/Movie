package com.example.mymovie.Call;



import com.example.mymovie.Dataclasses.Genre;
import com.example.mymovie.Dataclasses.TopRatedResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {


    @GET("movie/upcoming")
    public Call<TopRatedResponse> getTopRatedMoviesPage(@Query("api_key") String apiKey,
                                                        @Query("page") Integer page);

    @GET("genre/movie/list")
    public Call<Genre> getGenres(@Query("api_key") String apiKey);

    @GET("search/movie")
    public Call<TopRatedResponse> getSearch(@Query("api_key") String apiKey,
                                  @Query("query") String searchRequest,
                                  @Query("page") Integer page);








}
