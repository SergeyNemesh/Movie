package com.example.mymovie.ItemInfoActivity;

import com.example.mymovie.Dataclasses.Movie;

public interface ItemInfoContract {

    interface  ItemInfoView{

        void setSwitch();

        void setCheckState(boolean checkState);

        void doToastAdded();

        void doToastRemoved();
    }
    interface ItemInfoPresenter{

        void setPositionOfSwitchButton(Movie movie);

        void saveOrDeleteMovie(boolean checked, Movie movie);
    }
}
