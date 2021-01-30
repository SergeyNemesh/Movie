package com.example.mymovie.Collection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;

import com.example.mymovie.Collection.DialogCollection.DialogCollection;
import com.example.mymovie.CollectionAdapter.CollectionAdapter;
import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.ItemInfoActivity;
import com.example.mymovie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mymovie.R.string.remover_from_collection;

public class CollectionActivity extends AppCompatActivity implements CollectionContract.CollectionView, CollectionAdapter.CollectionListener, DialogCollection.OnItemCollectionClickForDelete {
    @BindView(R.id.gridView)
    GridView grid;
    @BindView(R.id.im_collection_back)
    ImageView btn_back;
    @BindView(R.id.textIsEmpty)
    TextView textHintIsEmpty;

    private CollectionAdapter adapter;
    private final static int REQUEST_CODE_REMOVE_ITEM = 1000;
    //Movie testMovieDelete;

    //---------------------------------------------
    CollectionContract.CollectionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        presenter = new CollectionPresenter(this);
        presenter.createDataBase(new DataBase(this));
        presenter.getListItemFromDb();
    }
    //----------------PopulateAdapter----------------------

    @Override
    public void populateAdapter(List<Movie> listData) {
        adapter = new CollectionAdapter(CollectionActivity.this, listData, this);
        grid.setAdapter(adapter);
    }
    //----------------выводим сообщение -------
    @Override
    public void setTextHintVisibility(int visible) {
      textHintIsEmpty.setVisibility(visible);
    }
    //-----------------------клик из адаптора------------------

    @Override
    public void onClickCollection(Movie movie, ImageView poster) {
        Intent intent = new Intent(CollectionActivity.this, ItemInfoActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(CollectionActivity.this, poster, ViewCompat.getTransitionName(poster));
        intent.putExtra("movie", movie);
        startActivityForResult(intent, REQUEST_CODE_REMOVE_ITEM,options.toBundle());
    }

    //------------------------клик из адаптера ДЛИННЫЙ
    @Override
    public void onLongDeleteItem(Movie movie) {
        //todo тут я слил Диалогу кино!!!!
        FragmentManager manager = getSupportFragmentManager();
        DialogCollection ddd = new DialogCollection(this,movie);
        ddd.show(manager, "myDialog");
    }

    //-------------------------------Клик из Аллерт Диалог------
    @Override
    public void onClickAskForDelete(Movie movie) {
        presenter.deleteMovie(movie);
        Toast.makeText(CollectionActivity.this, remover_from_collection, Toast.LENGTH_SHORT).show();
        adapter.removeItem(movie);
        presenter.getListItemFromDb();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.replyForActivityResult(resultCode,requestCode,REQUEST_CODE_REMOVE_ITEM);

    }

    @Override
    public void replaceListInAdapter(List<Movie> listData) {
        adapter.replaseData(listData);
    }
}
