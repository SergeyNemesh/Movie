package com.example.mymovie.Collection;

import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Movie;

import java.util.List;

public interface CollectionContract {


    interface CollectionView{

        void setTextHintVisibility(int visible);

        void populateAdapter(List<Movie> listData);


    }
    interface CollectionPresenter{

        void getListItemFromDb();


        void deleteMovie(Movie testMovieDelete);

        void createDataBase(DataBase dataBase);


    }
}
