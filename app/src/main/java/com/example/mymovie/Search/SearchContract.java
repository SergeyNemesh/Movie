package com.example.mymovie.Search;

import com.example.mymovie.Dataclasses.Movie;

import java.util.List;

public interface SearchContract {

    interface SearchView{

        void doToastNoResultsFromSearch(String requestMovie);

        void removeLoadingToAdapter();

        void addItemsToAdapter(List<Movie> movies);

        void setRefreshing(boolean b);

        void addLoadingToAdapter();

        void setToast();
    }
    interface SearchPresenter{

        void setCurrentPage();

        void startSearchingMovie(String requestMovie);

        void getGenres();

        void loadMoreItems(String searchingMovie);

        boolean booleanIsLoading();
    }
}
