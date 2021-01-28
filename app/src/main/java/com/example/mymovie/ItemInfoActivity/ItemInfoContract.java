package com.example.mymovie.ItemInfoActivity;

import com.example.mymovie.Dataclasses.Movie;

public interface ItemInfoContract {

    interface  ItemInfoView{

        void setSwitch();
    }
    interface ItemInfoPresenter{

        void setPositionOfSwitchButton(Movie movie);
    }
}
