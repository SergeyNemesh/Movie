package com.example.mymovie.Main;

import android.widget.Toast;

import com.example.mymovie.Call.ApiClient;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Genre;
import com.example.mymovie.Dataclasses.GenresInfo;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.Dataclasses.TopRatedResponse;
import com.example.mymovie.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mymovie.Pagination.PaginationListener.PAGE_START;

public class MainPresenter implements MainContract.MainPresenter {


    Map<Integer, String> mapOfGenres = new HashMap<>();
    private int currentPage = PAGE_START;
    private int totalPages = 0;
    private boolean isLoading = false;
//-----------------------------------------------------
    MainContract.MainView view;
    MainPresenter(MainContract.MainView view) {
        this.view = view;
    }
//--------------------------------------------------------------------------------
    //todo получил жанры
    @Override
    public void getGenres() {

        Call call = new ApiClient().getAPIService().getGenres(Constans.API_KEY);

        call.enqueue(new Callback<Genre>() {

            @Override
            public void onResponse(Call<Genre> call, Response<Genre> response) {
                getListOfGenres(response.body().getGenres());
            }

            @Override
            public void onFailure(Call<Genre> call, Throwable t) {
            }
        });
    }
    //todo записал в МАР
    public void getListOfGenres(List<GenresInfo> genres) {
        for (GenresInfo item : genres) {
            mapOfGenres.put(item.getId(), item.getName());
        }
    }

//todo получил фильмы
    @Override
    public void getMovies() {
        Call call = new ApiClient().getAPIService().getTopRatedMoviesPage(Constans.API_KEY, currentPage);
        call.enqueue(new Callback<TopRatedResponse>() {

            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {

                totalPages = response.body().getTotal_pages();

                if (currentPage != PAGE_START) view.removeLoadingToAdapter();
                //todo записал жанры для фильмов
                view.addItemsToAdapter(findGenres(response.body().getResults()));
                view.setRefreshing(false);


                if (currentPage < totalPages) {
                    view.addLoadingToAdapter();
                }
                isLoading = false;

            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {
                view.setRefreshing(false);
            }
        });
    }
    private List<Movie> findGenres(List<Movie> listMovies) {
        for (Movie movie : listMovies) {
            List<Integer> list = movie.getGenreIds();
            List<String> listStrings = new ArrayList<>();
            for (Integer id : list) {
                String s = mapOfGenres.get(id);
                listStrings.add(s);
            }
            movie.setGenres(listStrings);
        }
        return listMovies;
    }
    //---------------------------------------------------------------------
////---------------------------------------------------
    //todo ScrollListener Пагинация

    @Override
    public void loadMoreItems() {
        isLoading = true;
        currentPage++;
        if (currentPage > totalPages) {
            view.setToast();
            return;
        } else {
            getMovies();
        }
    }

    @Override
    public boolean booleanIsLoading() {
        return isLoading;
    }
//-------------------------------------------------
      //todo Swiperefresh----------------

    @Override
    public void setCurrentPage() {
        currentPage = PAGE_START;
    }
}

