package com.example.mymovie.Search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.ItemInfoActivity;
import com.example.mymovie.MainAdapter.Adaptor;
import com.example.mymovie.Pagination.PaginationListener;
import com.example.mymovie.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements SearchContract.SearchView,SearchView.OnQueryTextListener, Adaptor.OnItemClick {

    @BindView(R.id.recyclerViewSearch)
    RecyclerView recycler;
    @BindView(R.id.swipeRefreshSearch)
    SwipeRefreshLayout swipeRefreshSearch;
    @BindView(R.id.texthint)
    TextView texthint;
    @BindView(R.id.search_id)
    SearchView searchView;

    private Adaptor mAdaptor;

    //todo почему не работает ButterKnife
    //    @BindView(R.id.btn_search)
//     Button button;
    Button button;

    SearchContract.SearchPresenter presenter;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        presenter = new SearchPresenter(this);

        //todo почему не работает ButterKnife
        button = findViewById(R.id.btn_search);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        mAdaptor = new Adaptor(this, this);
        recycler.setAdapter(mAdaptor);

        searchView.setOnQueryTextListener(this);
        searchView.setIconified(false);
        searchView.setBackgroundColor(R.color.black);


        swipeRefreshSearch.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.setCurrentPage();
                mAdaptor.clear();
                swipeRefreshSearch.setRefreshing(true);
                presenter.startSearchingMovie(searchView.getQuery().toString());


            }
        });

        recycler.addOnScrollListener(new PaginationListener(layoutManager) {

            @Override
            protected void loadMoreItems() {
                presenter.loadMoreItems(searchView.getQuery().toString());
            }

            @Override
            public boolean isLoading() {

                return presenter.booleanIsLoading();
            }
            //---------------------------------
        });

          presenter.getGenres();
    }

//---------------------------------
    @Override
    public void addItemsToAdapter(List<Movie> movies) {
        mAdaptor.addItems(movies);
    }

    @Override
    public void setRefreshing(boolean b) {
        swipeRefreshSearch.setRefreshing(false);
    }

    @Override
    public void addLoadingToAdapter() {
        mAdaptor.addLoading();
    }

    //-----------------Toast from search---------
    @Override
    public void doToastNoResultsFromSearch(String requestMovie) {
        Toast.makeText(SearchActivity.this, getString(R.string.no_results_by_query,requestMovie), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void removeLoadingToAdapter() {
        mAdaptor.removeLoading();
    }

    //---------------------SearchView---------------------------
    @Override
    public boolean onQueryTextSubmit(String query) {
        presenter.setCurrentPage();
        mAdaptor.clear();
        presenter.startSearchingMovie(query);
        texthint.setVisibility(View.INVISIBLE);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    //---------------------------------------

//-------------------Click From Adaptor--------
    @Override
    public void onClick(Movie movie, ImageView poster) {
        Intent intent = new Intent(this, ItemInfoActivity.class);
        intent.putExtra("movie",movie);
        startActivity(intent);

    }
   //-------------------Scroller-------------------------

    @Override
    public void setToast() {
        Toast.makeText(SearchActivity.this, R.string.endOFResults, Toast.LENGTH_SHORT).show();
    }
}