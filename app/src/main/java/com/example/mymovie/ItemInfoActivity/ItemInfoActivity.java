package com.example.mymovie.ItemInfoActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.mymovie.DataBase.DataBase;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.ItemInfoActivity.DialogInfo.Dialog;
import com.example.mymovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import static com.example.mymovie.R.string.addedToColection;
import static com.example.mymovie.R.string.removedFromCollection;

public class ItemInfoActivity extends AppCompatActivity implements ItemInfoContract.ItemInfoView {

    @BindView(R.id.tv_info_title)
    TextView tvTitle;
    @BindView(R.id.tv_info_overview)
    TextView tvOverview;
    @BindView(R.id.tv_info_vote)
    TextView tvVote;
    @BindView(R.id.tv_info_release)
    TextView tvRelease;
    @BindView(R.id.tv_info_ganre)
    TextView tvGenre;
    @BindView(R.id.image_info)
    ImageView ivPoster;
    @BindView(R.id.btn_trailler)
    Button btnTrailer;
    @BindView(R.id.sw_collection)
    Switch swSaveMovie;

    ItemInfoContract.ItemInfoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
        ButterKnife.bind(this);
        //ivPoster.setTransitionName("poster");
        presenter = new ItemInfoPresenter(this);
        presenter.createDataBase(new DataBase(this));
        supportStartPostponedEnterTransition();
//-----------------------------------------------------------------------
        presenter.processIntent(getIntent());
        presenter.setTextData();
       //todo тут со старта сдвигаем свич
        presenter.setPositionOfSwitchButton();

    }

    //--------- проверка и установка позиции свитча-кнопки
    @Override
    public void setSwitch() {
        //todo пхд тут Кликается свитч
        swSaveMovie.setChecked(true);
    }

    //----------------------------SetInfo-----------------
    @Override
    public void setTitle(final String title) {
        tvTitle.setText(title);
    }

    @Override
    public void setRelease(final String releaseDate) {
        tvRelease.setText(releaseDate);
    }

    @Override
    public void setVote(final String vote) {
        tvVote.setText(vote);
    }

    @Override
    public void setOverView(final String overView) {
        tvOverview.setText(overView);
    }

    @Override
    public void setGenre(final String genre) {
        tvGenre.setText(genre);
    }

    @Override
    public void setPoster(final String posterPath) {
        Glide.with(this)
                .load(posterPath)
                //todo не срабатывает плейсхолдер
                //.placeholder(R.drawable.noimage)
//                .centerCrop()
//                .circleCrop()
                .into(ivPoster);
    }



    //------------------------кнопки--------------
    @OnClick(R.id.btn_trailler)
    public void onButtonTrailler() {
        FragmentManager manager = getSupportFragmentManager();
        //-------------------------//todo кликер прям в конструкторе
        Dialog myDialogFragment = new Dialog(new Dialog.OnTrailerClick() {
            @Override
            public void onClickTrailer() {
                //------------Клик из диалога----------
                Intent browserIntent = new
                        Intent(Intent.ACTION_VIEW, Uri.parse(Constans.YOU_TUBE + tvTitle.getText()));
                startActivity(browserIntent);
            }
        });
        myDialogFragment.show(manager, "myDialog");
    }

    //----------------Click Switch------------------
    @OnCheckedChanged(R.id.sw_collection)
    public void onSwitchClick(boolean isChecked) {
        presenter.saveOrDeleteMovie(isChecked);
    }

    @Override
    public void doToastAdded() {
        Toast.makeText(ItemInfoActivity.this, addedToColection, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doToastRemoved() {
        Toast.makeText(ItemInfoActivity.this, removedFromCollection, Toast.LENGTH_SHORT).show();
    }


 //---------тут не работает анимация//todo
    @Override
    public void onBackPressed() {
        presenter.onBackPressed();

    }
    @Override
    public void finishActivity(Movie movie) {
        setResult(RESULT_OK, new Intent().putExtra("movie", movie));
        //todo тут убивает анимацию
        finish();
    }

    @Override
    public void finishActivity() {
        //todo тут убивает анимацию
        finish();

    }
}