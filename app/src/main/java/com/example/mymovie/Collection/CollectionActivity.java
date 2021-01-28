package com.example.mymovie.Collection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mymovie.CollectionAdapter.CollectionAdapter;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity implements CollectionContract.CollectionView {
    @BindView(R.id.gridView)
    GridView grid;
    @BindView(R.id.im_collection_back)
    ImageView btn_back;
    @BindView(R.id.textIsEmpty)
    TextView textHintIsEmpty;

    CollectionAdapter adapter;

    private final static int REQUEST_CODE_REMOVE_ITEM = 1000;

    Movie testMovieDelete;



   //---------------------------------------------
    CollectionContract.CollectionPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        presenter = new CollectionPresenter(this);

//        adapter = new CollectionAdapter(CollectionActivity.this, listData, this);
//        grid.setAdapter(adapter);

        presenter.getListItemFromDb();
    }



}