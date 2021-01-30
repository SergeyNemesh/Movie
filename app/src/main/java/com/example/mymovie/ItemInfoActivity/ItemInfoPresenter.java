package com.example.mymovie.ItemInfoActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Movie;

import java.util.ArrayList;
import java.util.List;

public class ItemInfoPresenter implements ItemInfoContract.ItemInfoPresenter {

    private DataBase dataHelper;
    private Movie movie;
    //-----тут по другому работает свич
    private boolean newCheckState = false;
    private ItemInfoContract.ItemInfoView view;

    public ItemInfoPresenter(ItemInfoContract.ItemInfoView view) {
        this.view = view;
    }

    @Override
    public void createDataBase(DataBase dataBase) {
        dataHelper = dataBase;
    }

    @Override
    public void processIntent(Intent intent) {
        if (intent != null) {
            movie = intent.getParcelableExtra("movie");
        }
    }

    @Override
    public void setTextData() {
        if (movie != null) {
            view.setTitle(movie.getTitle() == null ? "" : movie.getTitle());
            view.setRelease(movie.getReleaseDate() == null ? "" : movie.getReleaseDate());
            view.setVote(String.valueOf(movie.getVoteAverage()));
            view.setOverView(movie.getOverview() == null ? "" : movie.getOverview());
            view.setGenre(movie.getGenres() == null ? "" : movie.getGenres().toString());
            view.setPoster(Constans.URL_FOR_PICTURE + movie.getPosterPath());
        }
    }

    @Override
    public void setPositionOfSwitchButton() {
        List<Integer> titlesToChek = readFromDb();
        for (Integer item : titlesToChek) {
            if (item.equals(movie.getId())) {
                view.setSwitch();
                //-----тут по другому работает свич
                newCheckState = true;
                break;
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
    public void saveOrDeleteMovie(boolean save) {
        SQLiteDatabase sql = dataHelper.getWritableDatabase();
        if (save) {
            newCheckState = true;
            ContentValues contentValues = new ContentValues();
            contentValues.put(DataBase.MOVIE_ID, movie.getId());
            contentValues.put(DataBase.TITLE, movie.getTitle());
            contentValues.put(DataBase.POSTER, movie.getPosterPath());
            contentValues.put(DataBase.OVERVIEW, movie.getOverview());
            contentValues.put(DataBase.DATA, movie.getReleaseDate());
            contentValues.put(DataBase.RATE, movie.getVoteAverage());
            contentValues.put(DataBase.GENRES, String.valueOf(movie.getGenres()));
            sql.insert(DataBase.TABLE_NAME, null, contentValues);
            //todo тост!!
            //view.doToastAdded();
        } else {
            newCheckState = false;
            sql.execSQL(DataBase.DEL_NAME + movie.getId());
            view.doToastRemoved();
        }
        dataHelper.close();
    }
}
