package com.example.mymovie.CollectionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.R;

import java.util.List;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.Holder> {

    private Context context;

    private List<Movie> listOfFilms;
    private CollectionListener clickListener;


    public NewAdapter(Context context, List<Movie> listOfFilms, CollectionListener clickListener) {
        this.clickListener = clickListener;
        this.listOfFilms = listOfFilms;
        this.context = context;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Movie movie = listOfFilms.get(position);
        holder.title_movie.setText(movie.getTitle());
        Glide.with(context).load(Constans.URL_FOR_PICTURE + movie.getPosterPath()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return listOfFilms.size();
    }




    class Holder extends RecyclerView.ViewHolder {


        TextView title_movie;
        ImageView poster;

        public Holder(@NonNull View itemView) {
            super(itemView);
            title_movie = itemView.findViewById(R.id.gv_title);
            poster = itemView.findViewById(R.id.gv_poster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onClickCollection(listOfFilms.get(getAdapterPosition()), poster);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onLongDeleteItem(listOfFilms.get(getAdapterPosition()));
                    return false;
                }
            });
        }
    }

    public void removeItem(Movie movie) {
        listOfFilms.remove(movie);
        notifyDataSetChanged();
    }

    public interface CollectionListener {
        void onClickCollection(Movie movie, ImageView poster);
        void onLongDeleteItem(Movie movie);
    }

}
