package com.example.mymovie.Collection;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.mymovie.Collection.DialogCollection.DialogCollection;
import com.example.mymovie.CollectionAdapter.CollectionAdapter;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.ItemInfoActivity;
import com.example.mymovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectionActivity extends AppCompatActivity implements CollectionContract.CollectionView, CollectionAdapter.CollectionListener, DialogCollection.OnItemCollectionClickForDelete {
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


        presenter.getListItemFromDb();

        //todo хранить в Активити лист?? или нельзя

//        adapter = new CollectionAdapter(CollectionActivity.this, listData, this);
//        grid.setAdapter(adapter);


        //todo сюда вернуть Лист?

    }

    //----------------выводим сообщение -------
    @Override
    public void setTextHintVisibility(int visible) {
        //todo ьудет ли тут разные значения
        textHintIsEmpty.setVisibility(visible);

    }

    //-----------------------клик из адаптора------------------

    @Override
    public void onClickCollection(Movie movie, ImageView poster) {
        Intent intent = new Intent(CollectionActivity.this, ItemInfoActivity.class);
        intent.putExtra("movie", movie);
        startActivityForResult(intent, REQUEST_CODE_REMOVE_ITEM);
    }
//------------------------клик из адаптера ДЛИННЫЙ
    @Override
    public void onLongDeleteItem(Movie movie) {
        FragmentManager manager = getSupportFragmentManager();
        DialogCollection ddd = new DialogCollection(this);
        ddd.show(manager, "myDialog");
        testMovieDelete = movie;
    }
//-------------------------------Клик из Аллерт Диалог------
    @Override
    public void onClickAskForDelete() {

        presenter.deleteMovie(testMovieDelete);

        Toast.makeText(CollectionActivity.this, "Removed from collection", Toast.LENGTH_SHORT).show();
        adapter.removeItem(testMovieDelete);

        //presenter.readFromDb();
        //todo какой выбрать?
        presenter.getListItemFromDb();


        //todo походу отпало
//        if (listData.isEmpty()) {
//            textHintIsEmpty.setVisibility(View.VISIBLE);
//        } else {
//            textHintIsEmpty.setVisibility(View.INVISIBLE);
//        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //todo как тут поступить??
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_REMOVE_ITEM) {
            listData.clear();
            getListItemFromDb();
            adapter.replaseData(listData);
        }
    }
}
