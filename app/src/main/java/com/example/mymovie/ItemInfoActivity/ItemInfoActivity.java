package com.example.mymovie.ItemInfoActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.DialogInfo.Dialog;
import com.example.mymovie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemInfoActivity extends AppCompatActivity implements ItemInfoContract.ItemInfoView,Dialog.OnTrailerClick, View.OnClickListener {
    //-----------------------
    @BindView(R.id.tv_info_title)
    TextView title;
    @BindView(R.id.tv_info_overview)
    TextView overview;
    @BindView(R.id.tv_info_vote)
    TextView vote;
    @BindView(R.id.tv_info_release)
    TextView release;
    @BindView(R.id.tv_info_ganre)
    TextView genre;
    @BindView(R.id.image_info)
    ImageView poster;
    @BindView(R.id.btn_trailler)
    Button button_trailer;
    @BindView(R.id.sw_collection)
    Switch aSwitch;
    //-----------------------
    private Movie movie;
    private Boolean newCheckState = null;




    //----------------------
    ItemInfoContract.ItemInfoPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        ButterKnife.bind(this);
        presenter = new ItemInfoPresenter(this);
        aSwitch.setOnClickListener(this);

        FragmentManager manager = getSupportFragmentManager();
        Dialog myDialogFragment = new Dialog(this);
//-----------------------------------------------------------------------
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movie");

        title.setText(movie.getTitle());
        release.setText(getString(R.string.release_date, movie.getReleaseDate()));
        vote.setText(getString(R.string.vote_rate, String.valueOf(movie.getVoteAverage())));
        overview.setText(movie.getOverview());
        genre.setText(movie.getGenres().toString());
        Glide.with(this)
                .load(Constans.URL_FOR_PICTURE + movie.getPosterPath())
                .into(poster);




        supportStartPostponedEnterTransition();


        presenter.setPositionOfSwitchButton(movie);

    }
//--------- проверка и установка позиции свитча-кнопки
    @Override
    public void setSwitch() {
        aSwitch.setChecked(true);
    }

    //------------------------кнопки--------------
    @OnClick(R.id.btn_trailler)
    public void onButtonTrailler(){
        //todo как сюда отправить манагер и диалог??
        myDialogFragment.show(manager, "myDialog");

    }



//------------Клик из диалога----------
    @Override
    public void onClickTrailer() {
        Intent browserIntent = new
                Intent(Intent.ACTION_VIEW, Uri.parse(Constans.YOU_TUBE + title.getText()));
        startActivity(browserIntent);
    }
//----------------Click Switch------------------
    @Override
    public void onClick(View v) {
        boolean checked = ((Switch) v).isChecked();
        presenter.saveOrDeleteMovie(checked,movie);

    }

    @Override
    public void setCheckState(boolean checkState) {
        //тру или фалс
        newCheckState = checkState;
    }

    @Override
    public void doToastAdded() {
        Toast.makeText(ItemInfoActivity.this, "Added to your collection", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doToastRemoved() {
        Toast.makeText(ItemInfoActivity.this, "Removed from collection", Toast.LENGTH_SHORT).show();
    }
//-------------------------------------------------------------------------

    //todo как тут быть????
    @Override
    public void onBackPressed() {
        if(newCheckState!=null){
            if(!newCheckState){
                setResult(RESULT_OK,new Intent().putExtra("movie",movie));

            }
        }
        super.onBackPressed();
    }
}