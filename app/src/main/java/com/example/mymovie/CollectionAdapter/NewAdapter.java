package com.example.mymovie.CollectionAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.R;

import java.util.ArrayList;
import java.util.List;


public class NewAdapter extends RecyclerView.Adapter<NewAdapter.Holder> {

    private Context context;
    List<Movie> listOfFilms = new ArrayList<>();


    public NewAdapter(Context context) {
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

    }

    @Override
    public int getItemCount() {
        return listOfFilms.size();
    }

    class Holder extends RecyclerView.ViewHolder {





        public Holder(@NonNull View itemView) {
            super(itemView);




        }
    }


}
