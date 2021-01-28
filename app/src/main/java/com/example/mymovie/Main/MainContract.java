package com.example.mymovie.Main;

import com.example.mymovie.Dataclasses.Movie;

import java.util.List;

public interface MainContract {

    interface MainView{

        void removeLoadingToAdapter();

        void addItemsToAdapter(List<Movie> movies);

        void setRefreshing(boolean setRefreshFalse);

        void addLoadingToAdapter();

        void setToast();
    }
    interface MainPresenter{

        void getGenres();

        void getMovies();


        void loadMoreItems();

        boolean booleanIsLoading();
    }
}
