package com.example.mymovie.Collection;

public class CollectionPresenter implements CollectionContract.CollectionPresenter {

    CollectionContract.CollectionView view;

    CollectionPresenter(CollectionContract.CollectionView view){
        this.view=view;
    }

    @Override
    public void getListItemFromDb() {

    }
}
