package com.example.mymovie.ItemInfoActivity;

import android.content.Intent;

import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Movie;

public interface ItemInfoContract {

    interface ItemInfoView {

        void setSwitch();

        void doToastAdded();

        void doToastRemoved();

        void setTitle(String title);

        void setRelease(String releaseDate);

        void setVote(String vote);

        void setOverView(String overView);

        void setGenre(String genre);

        void setPoster(String posterPath);


    }

    interface ItemInfoPresenter {

        void setPositionOfSwitchButton();

        void saveOrDeleteMovie(boolean save);

        void createDataBase(DataBase dataBase);

        void processIntent(Intent intent);

        void setTextData();



    }
}
