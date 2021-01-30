package com.example.mymovie.Main;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.mymovie.Collection.CollectionActivity;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.ItemInfoActivity;
import com.example.mymovie.MainAdapter.Adaptor;
import com.example.mymovie.Pagination.PaginationListener;
import com.example.mymovie.R;
import com.example.mymovie.Search.SearchActivity;
import com.example.mymovie.Splash.SplashActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements MainContract.MainView, Adaptor.OnItemClick {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_collection)
    ImageView btn_collection;
    @BindView(R.id.btn_search)
    Button buttonSearch;
    @BindView(R.id.lineartest)
    LinearLayout linearLayout;

    private Adaptor mAdaptor;


    MainContract.MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
        ButterKnife.bind(this);
        SplashActivity.first.finish();


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdaptor = new Adaptor(this, this);
        mRecyclerView.setAdapter(mAdaptor);
//------------------------кроллер------------------------
        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                presenter.loadMoreItems();
            }

            @Override
            public boolean isLoading() {
                return presenter.booleanIsLoading();
            }

        });
//----------------------------свайпРЕфреш-----------------------
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.setCurrentPage();
                mAdaptor.clear();
                swipeRefresh.setRefreshing(true);
                presenter.getMovies();

            }
        });
        presenter.getGenres();
        presenter.getMovies();
    }


    @Override
    public void removeLoadingToAdapter() {
        mAdaptor.removeLoading();
    }

    @Override
    public void addItemsToAdapter(List<Movie> movies) {
        mAdaptor.addItems(movies);
    }

    @Override
    public void setRefreshing(boolean setRefreshFalse) {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void addLoadingToAdapter() {
        mAdaptor.addLoading();
    }

    @Override
    public void setToast() {
        Toast.makeText(MainActivity.this, R.string.endOFResults, Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------
    @OnClick(R.id.iv_collection)
    public void onButtonCollection() {
        Intent intentCollection = new Intent(MainActivity.this, CollectionActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, btn_collection, ViewCompat.getTransitionName(btn_collection));
        startActivity(intentCollection, options.toBundle());

    }

    @OnClick(R.id.btn_search)
    public void onButtonSearch() {
        Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, buttonSearch, ViewCompat.getTransitionName(buttonSearch));
        startActivity(intentSearch, options.toBundle());
    }

    @Override
    public void onClick(Movie movie, ImageView poster) {
        Intent intent = new Intent(this, ItemInfoActivity.class);
        intent.putExtra("movie", movie);
        List<Pair<View, String>> listOfViews = new ArrayList<>();
        listOfViews.add(Pair.create((View) poster, "poster"));
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this, listOfViews.toArray(new android.util.Pair[]{})).toBundle();
        startActivity(intent, bundle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        linearLayout.setVisibility(View.INVISIBLE);
    }
}