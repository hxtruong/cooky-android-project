package com.hxtruonglhsang.cooky.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.hxtruonglhsang.cooky.LoginActivity;
import com.hxtruonglhsang.cooky.MainActivity;
import com.hxtruonglhsang.cooky.R;
import com.hxtruonglhsang.cooky.fragment.HomeFragment;
import com.hxtruonglhsang.cooky.service.Firebase;

public class SplashScreen extends AppCompatActivity {
    private Handler mWaitHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.initializeApp();

        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
            getSupportActionBar().hide(); // hide the title bar
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        } catch (Exception e) {

        }
        setContentView(R.layout.activity_splash_screen);
        mWaitHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Intent intent = null;
                    if (Firebase.isSignedIn()) {
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                    } else {
                        intent = new Intent(getApplicationContext(), LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();
                } catch (Exception ignored) {
                    ignored.printStackTrace();
                }
            }
        }, 1500);  // Give a 2 seconds delay.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mWaitHandler.removeCallbacksAndMessages(null);
    }
}
