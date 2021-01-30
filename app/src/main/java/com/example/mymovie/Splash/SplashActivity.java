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
    TextView t4;
    @BindView(R.id.textView2)
    TextView t3;
    @BindView(R.id.textView3)
    TextView t2;
    @BindView(R.id.textView4)
    TextView t1;
    public static SplashActivity first;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        first = this;
        t1.setTransitionVisibility(View.INVISIBLE);
        t2.setTransitionVisibility(View.INVISIBLE);
        t3.setTransitionVisibility(View.INVISIBLE);
        t3.setTransitionVisibility(View.INVISIBLE);
        t4.setTransitionVisibility(View.INVISIBLE);


        Animation a1up = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1up);
        t1.startAnimation(a1up);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a1down = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1down);
                t1.startAnimation(a1down);

            }
        }, 1500);
//----------------------------------------------------------------------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a2up = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1up);
                t2.startAnimation(a2up);

            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a2down = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1down);
                t2.startAnimation(a2down);

            }
        }, 2500);
        //--------------------------------------------------------------------------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a3up = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1up);
                t3.startAnimation(a3up);

            }
        }, 2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a3down = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1down);
                t3.startAnimation(a3down);

            }
        }, 3500);
//-------------------------------------------------------------------------
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a4up = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1up);
                t4.startAnimation(a4up);

            }
        }, 3000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation a4down = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.anim1down);
                t4.startAnimation(a4down);

            }
        }, 4500);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, t4, ViewCompat.getTransitionName(t4));
                startActivity(intent, options.toBundle());

            }
        }, 4500);

    }
}