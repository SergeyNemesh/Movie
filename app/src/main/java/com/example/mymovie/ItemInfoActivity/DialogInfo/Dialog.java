package com.example.mymovie.ItemInfoActivity.DialogInfo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.mymovie.R;


public class Dialog extends AppCompatDialogFragment {

    public OnTrailerClick clickTrailer;

    public Dialog(OnTrailerClick clickTrailer){
        this.clickTrailer=clickTrailer;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.aru_s_to_search))
                .setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        clickTrailer.onClickTrailer();
                        dialog.cancel();
                    }
                });

        return builder.create();
    }

    public interface OnTrailerClick {
        void onClickTrailer();
    }

}
