package com.example.mymovie.CollectionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mymovie.Dataclasses.Constans;
import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.R;

import java.util.List;

public class CollectionAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Movie> listData;
    private CollectionListener clickListener;

    public CollectionAdapter(Context context, List<Movie> listData, CollectionListener clickListener) {
        this.context = context;
        this.listData = listData;
        this.clickListener = clickListener;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.grid_item, null);
        }
        ImageView poster = convertView.findViewById(R.id.gv_poster);
        TextView title = convertView.findViewById(R.id.gv_title);

        Movie movie = listData.get(position);

        title.setText(movie.getTitle());

        String posterPath = movie.getPosterPath();
        String s = Constans.URL_FOR_PICTURE + posterPath;
        Glide.with(context).load(s).into(poster);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onClickCollection(movie, poster);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                clickListener.onLongDeleteItem(movie);
                return false;
            }
        });
        return convertView;
    }

    public interface CollectionListener {
        void onClickCollection(Movie movie, ImageView poster);
        void onLongDeleteItem(Movie movie);
    }

    public void removeItem(Movie movie) {
        listData.remove(movie);
        notifyDataSetChanged();
    }

    public void replaseData(List<Movie> movies) {
        listData.clear();
        listData.addAll(movies);
        notifyDataSetChanged();
    }

}
