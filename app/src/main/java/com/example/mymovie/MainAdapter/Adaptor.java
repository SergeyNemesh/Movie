package com.example.mymovie.MainAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.R;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Adaptor extends RecyclerView.Adapter<BaseViewHolder> {

    private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
    private Context context;
    private OnItemClick clickListener;
    private List<Movie> listMovies = new ArrayList<>();

    public Adaptor(Context context, OnItemClick clickListener) {
        this.context = context;
        this.clickListener = clickListener;
    }
    //--------------------------МЕТОД который говорит Адаптору какой Холдер использовать---------------------
    @Override
    public int getItemViewType(int position) {
        if (isLoaderVisible) {
            return position == listMovies.size() - 1 ? VIEW_TYPE_LOADING : VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_NORMAL;
        }
    }
    //------------------------Сейчас будем использовать-------------------------------------------------
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_upgrade, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.addanimation();
        holder.onBind(position);

    }

    @Override
    public int getItemCount() {
        return listMovies == null ? 0 : listMovies.size();
    }

    public void clear() {
        listMovies.clear();
        notifyDataSetChanged();

    }

    //---------------------------Методы догрузки----------------------------
    public void removeLoading() {
        isLoaderVisible = false;
        int position = listMovies.size() - 1;
        Movie movie = getItem(position);
        if (movie != null) {
            listMovies.remove(position);
            notifyItemRemoved(position);
        }
    }


    Movie getItem(int position) {  //todo метод который возвращяет позицию! которую мы сами сюда И прислали???
        return listMovies.get(position);
    }

    public void addItems(List<Movie> movies) {
        listMovies.addAll(movies);
        notifyDataSetChanged();
    }

    public void addLoading() {
        isLoaderVisible = true;
        listMovies.add(new Movie());
        notifyItemInserted(listMovies.size() - 1);
    }

    //--------111111-----------------
    public class Holder extends BaseViewHolder {
        @BindView(R.id.imageView)
        ImageView poster;
        @BindView(R.id.ll_anim)
        LinearLayout linear;
        @BindView(R.id.tv_titel)
        TextView title_movie;
        @BindView(R.id.tv_release)
        TextView release;
        @BindView(R.id.tv_vote)
        TextView vote;
        @BindView(R.id.tv_overview)
        TextView overview;
        @BindView(R.id.tv_genre)
        TextView genres;

        Holder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this,itemView);
             itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClick(listMovies.get(getAdapterPosition()),poster);
                }
            });
        }

        public void onBind(int position) {
            super.onBind(position);
            Movie movie = listMovies.get(position);
            title_movie.setText(movie.getTitle());
            release.setText("Release at " + movie.getReleaseDate());
            vote.setText("Rate : " + String.valueOf(movie.getVoteAverage()));
            overview.setText(movie.getOverview());
            String genresString = movie.getGenres().toString();

            genres.setText(genresString.substring(1,genresString.length()-1));

            Glide.with(context)
                    .load(Constans.URL_FOR_PICTURE + movie.getPosterPath())
                    .placeholder(R.drawable.noimage)
                    .into(poster);

        }

        @Override
        public void addanimation() {
            linear.setBackgroundResource(R.drawable.style_for_item_recycler);

        }
    }

    //---------2222222----------------------
    public class ProgressHolder extends BaseViewHolder {
        LinearLayout linearLayout;

        ProgressHolder(View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.ll_load_anim);
        }

        @Override
        public void addanimation() {
            linearLayout.setAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_for_item_recycler));
        }
    }

    public interface OnItemClick {
        void onClick(Movie movie, ImageView poster);
    }

}

