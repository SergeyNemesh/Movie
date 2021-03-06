package com.example.mymovie.Collection.DialogCollection;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mymovie.Dataclasses.Movie;
import com.example.mymovie.R;


public class DialogCollection extends AppCompatDialogFragment {


    public OnItemCollectionClickForDelete clickToDelete;
    //todo тут есть кино файнал
    private Movie testMovie;

    public DialogCollection(OnItemCollectionClickForDelete clickToDelete,final Movie testMovie) {
                this.clickToDelete =  clickToDelete;
                this.testMovie = testMovie;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.ask_to_delete_from_collection))
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clickToDelete.onClickAskForDelete(testMovie);
                        dialog.cancel();
                    }
                });

        return builder.create();
    }
    public interface OnItemCollectionClickForDelete {
         void onClickAskForDelete(Movie movie);
    }

}