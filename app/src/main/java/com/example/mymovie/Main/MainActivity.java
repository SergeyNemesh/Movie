package com.example.mymovie.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.ItemInfoActivity;
import com.example.mymovie.MainAdapter.Adaptor;
import com.example.mymovie.Pagination.PaginationListener;
import com.example.mymovie.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mymovie.Pagination.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity implements MainContract.MainView,Adaptor.OnItemClick{





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


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdaptor = new Adaptor(this, this);
        mRecyclerView.setAdapter(mAdaptor);

        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                presenter.loadMoreItems();
            }

            @Override
            public boolean isLoading() {
                //todo верну ли я тут значение переменной???
                return presenter.booleanIsLoading();
            }
            //---------------------------------
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = PAGE_START;
                mAdaptor.clear();
                swipeRefresh.setRefreshing(true);
                getMovies();

            }
        });

        presenter.getGenres();
        presenter.getMovies();
    }


    //todo методы из getMovies();
    @Override
    public void removeLoadingToAdapter() {
        mAdaptor.removeLoading();
    }
      //todo отправил лист в адаптор
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

    //----------------------


    @Override
    public void setToast() {
        Toast.makeText(MainActivity.this, R.string.endOFResults, Toast.LENGTH_SHORT).show();
    }

    //--------------------------------------
    @OnClick(R.id.iv_collection)
    public void onButtonCollection() {


    }

    @OnClick(R.id.btn_search)
    public void onButtonSearch() {


    }
   //todo клик из Адаптора
    @Override
    public void onClick(Movie movie, ImageView poster) {
        Intent intent = new Intent(this, ItemInfoActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);

    }
}