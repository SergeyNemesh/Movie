package com.example.mymovie.Collection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.Collection.DialogCollection.DialogCollection;
import com.example.mymovie.CollectionAdapter.NewAdapter;
import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.ItemInfoActivity;
import com.example.mymovie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.mymovie.R.string.remover_from_collection;

public class CollectionActivity extends AppCompatActivity implements CollectionContract.CollectionView, NewAdapter.CollectionListener, DialogCollection.OnItemCollectionClickForDelete {
    @BindView(R.id.gridView)
    RecyclerView recyclerView;
    @BindView(R.id.im_collection_back)
    ImageView btn_back;
    @BindView(R.id.textIsEmpty)
    TextView textHintIsEmpty;
    private NewAdapter adapter;

    CollectionContract.CollectionPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        presenter = new CollectionPresenter(this);
        presenter.createDataBase(new DataBase(this));
        presenter.getListItemFromDb();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }
    //----------------PopulateAdapter----------------------

    @Override
    public void populateAdapter(List<Movie> listData) {
        adapter = new NewAdapter(this, listData, this);
        recyclerView.setAdapter(adapter);
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
        startActivity(intent, options.toBundle());
    }

    //------------------------клик из адаптера ДЛИННЫЙ
    @Override
    public void onLongDeleteItem(Movie movie) {
        //todo тут я слил Диалогу кино!!!!
        //todo и отдал вниз
        FragmentManager manager = getSupportFragmentManager();
        DialogCollection ddd = new DialogCollection(this, movie);
        ddd.show(manager, "myDialog");
    }

    //-------------------------------Клик из Аллерт Диалог------
    //todo сюда
    @Override
    public void onClickAskForDelete(Movie movie) {
        presenter.deleteMovie(movie);
        Toast.makeText(CollectionActivity.this, remover_from_collection, Toast.LENGTH_SHORT).show();
        adapter.removeItem(movie);
        presenter.getListItemFromDb();
    }

    @Override
    protected void onResume() {
        presenter.getListItemFromDb();
        super.onResume();
    }
}
