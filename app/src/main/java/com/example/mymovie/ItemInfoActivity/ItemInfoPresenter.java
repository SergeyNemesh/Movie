package com.example.mymovie.ItemInfoActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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


}
