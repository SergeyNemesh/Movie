package com.example.mymovie.Search;

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

public class SearchPresenter implements SearchContract.SearchPresenter {

    Map<Integer, String> mapOfGenres = new HashMap<>();
    private int currentPage = PAGE_START;
    private int totalPages = 0;
    private boolean isLoading = false;



    SearchContract.SearchView view;
    SearchPresenter(SearchContract.SearchView view) {
        this.view = view;
    }


    //todo Swiperefresh----------------
    @Override
    public void setCurrentPage() {
        currentPage = PAGE_START;
    }

    @Override
    public void startSearchingMovie(String requestMovie) {
        Call call = new ApiClient().getAPIService().getSearch(Constans.API_KEY, requestMovie, currentPage);
        call.enqueue(new Callback<TopRatedResponse>() {
            @Override
            public void onResponse(Call<TopRatedResponse> call, Response<TopRatedResponse> response) {

                if (response.body().getResults().isEmpty()) {
                    view.doToastNoResultsFromSearch(requestMovie);
                } else {

                    totalPages = response.body().getTotal_pages();

                    if (currentPage != PAGE_START)  view.removeLoadingToAdapter();
                    view.addItemsToAdapter(findGenres(response.body().getResults()));

                    view.setRefreshing(false);

                    if (currentPage < totalPages) {
                        view.addLoadingToAdapter();
                    }
                    isLoading = false;
                }
            }

            @Override
            public void onFailure(Call<TopRatedResponse> call, Throwable t) {

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
//---------------------------Заполнили МАР-------------------------
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
    public void getListOfGenres(List<GenresInfo> genres) {
        for (GenresInfo item : genres) {
            mapOfGenres.put(item.getId(), item.getName());
        }
    }
    //------------------------СкроЛЛер--------------------------

    @Override
    public void loadMoreItems(String searchingMovie) {
        isLoading = true;
        currentPage++;
        if (currentPage > totalPages) {
            view.setToast();
            return;
        } else {
            startSearchingMovie(searchingMovie);

        }
    }

    @Override
    public boolean booleanIsLoading() {
        return isLoading;
    }
}
