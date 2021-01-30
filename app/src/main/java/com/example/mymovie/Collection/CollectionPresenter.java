package com.example.mymovie.Collection;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Movie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class CollectionPresenter implements CollectionContract.CollectionPresenter {

    private List<Movie> listData = new ArrayList<>();
    private DataBase dataHelper;

    CollectionContract.CollectionView view;

    CollectionPresenter(CollectionContract.CollectionView view) {
        this.view = view;

    }

    @Override
    public void createDataBase(DataBase dataBase) {
        dataHelper = dataBase;
    }

    @Override
    public void getListItemFromDb() {
        listData.clear();
        listData = readFromDb();
        if (listData.isEmpty()) {
            view.setTextHintVisibility(VISIBLE);
        } else {
            view.setTextHintVisibility(INVISIBLE);
        }
        view.populateAdapter(listData);
    }

    private List<Movie> readFromDb() {
        SQLiteDatabase sql = dataHelper.getWritableDatabase();
        List<Movie> list = new ArrayList<>();
        Cursor cursor = sql.query(DataBase.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int nameId = cursor.getColumnIndex(DataBase.MOVIE_ID);
            int nameTitle = cursor.getColumnIndex(DataBase.TITLE);
            int namePoster = cursor.getColumnIndex(DataBase.POSTER);
            int nameOver = cursor.getColumnIndex(DataBase.OVERVIEW);
            int nameDate = cursor.getColumnIndex(DataBase.DATA);
            int nameRate = cursor.getColumnIndex(DataBase.RATE);
            int nameGenres = cursor.getColumnIndex(DataBase.GENRES);
            do {
                Movie f = new Movie();
                //todo пхд тута вешаются кавычки
                f.setId(cursor.getInt(nameId));
                f.setTitle(cursor.getString(nameTitle));
                f.setPosterPath(cursor.getString(namePoster));
                f.setOverview(cursor.getString(nameOver));
                f.setReleaseDate(cursor.getString(nameDate));
                f.setVoteAverage(Float.parseFloat(cursor.getString(nameRate)));
                f.setGenres(Collections.singletonList(cursor.getString(nameGenres)));
                list.add(f);
            } while (cursor.moveToNext());
        } else
            cursor.close();
        closeDb();
        return list;

    }

    private void closeDb() {
        dataHelper.close();
    }

    @Override
    public void deleteMovie(Movie testMovieDelete) {
        SQLiteDatabase sql = dataHelper.getWritableDatabase();
        sql.execSQL(DataBase.DEL_NAME + testMovieDelete.getId());
        dataHelper.close();


    }

}
