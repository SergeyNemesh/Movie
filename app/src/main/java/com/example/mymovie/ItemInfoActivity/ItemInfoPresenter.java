package com.example.mymovie.ItemInfoActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Movie;

import java.util.ArrayList;
import java.util.List;

public class ItemInfoPresenter implements ItemInfoContract.ItemInfoPresenter{

         //todo как создать базу??
     DataBase dataHelper;

    //-----------------------------

    ItemInfoContract.ItemInfoView view;
    ItemInfoPresenter(ItemInfoContract.ItemInfoView view){
        this.view=view;
        //this.dataHelper=dataHelper;
    }


    //----------------------

    @Override
    public void setPositionOfSwitchButton(Movie movie) {
        List<Integer> titlesToChek = readFromDb();
        for (Integer item : titlesToChek) {
            if (item.equals(movie.getId())) {
                view.setSwitch();

            }
        }
    }
    public List<Integer> readFromDb() {
        SQLiteDatabase sql = dataHelper.getWritableDatabase();
        List<Integer> idCheck = new ArrayList<>();
        Cursor cursor = sql.query(DataBase.TABLE_NAME, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int overID = cursor.getColumnIndex(DataBase.MOVIE_ID);
            do {
                Integer s = Integer.parseInt(cursor.getString(overID));

                idCheck.add(s);

            } while (cursor.moveToNext());
        } else
            cursor.close();
        dataHelper.close();
        return idCheck;

    }
//-------------------из СВИТЧА------------------
    @Override
    public void saveOrDeleteMovie(boolean checked, Movie movie) {
        SQLiteDatabase sql = dataHelper.getWritableDatabase();

        if (checked) {
            view.setCheckState(true);
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBase.MOVIE_ID, movie.getId());
            contentValues.put(DataBase.TITLE, movie.getTitle());
            contentValues.put(DataBase.POSTER, movie.getPosterPath());
            contentValues.put(DataBase.OVERVIEW, movie.getOverview());
            contentValues.put(DataBase.DATA, movie.getReleaseDate());
            contentValues.put(DataBase.RATE, movie.getVoteAverage());
            contentValues.put(DataBase.GENRES, String.valueOf(movie.getGenres()));
            sql.insert(DataBase.TABLE_NAME, null, contentValues);
            view.doToastAdded();
        } else {
            view.setCheckState(false);
            sql.execSQL(DataBase.DEL_NAME + movie.getId());
            view.doToastRemoved();
        }
        dataHelper.close();
    }
}
