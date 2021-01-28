package com.example.mymovie.Collection;

import com.example.mymovie.Dataclasses.Movie;

public interface CollectionContract {


    interface CollectionView{

        void setTextHintVisibility(int visible);
    }
    interface CollectionPresenter{

        void getListItemFromDb();


        void deleteMovie(Movie testMovieDelete);
    }
}
