package com.example.mymovie.Splash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.mymovie.Main.MainActivity;
import com.example.mymovie.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.textView)
     TextView t1;
    @BindView(R.id.textView2)
    TextView text;
    public static SplashActivity first;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
       first = this;
        //t1.setTransitionVisibility(View.INVISIBLE);
        //text.setTransitionVisibility(View.INVISIBLE);

        Animation a1up = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1up);
        t1.startAnimation(a1up);
        Animation textanim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animtext);
        text.startAnimation(textanim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, t1, ViewCompat.getTransitionName(t1));
                startActivity(intent, options.toBundle());
                t1.setClickable(true);
            }
        }, 2299);



    }
}