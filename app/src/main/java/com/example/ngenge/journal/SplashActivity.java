package com.example.ngenge.journal;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.common.oob.SignUp;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 2100;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(FirebaseAuth.getInstance().getCurrentUser()
                        != null)
                {
                    startActivity(new Intent(SplashActivity.this,
                            MainActivity.class));
                }

                else {
                    startActivity(new Intent(SplashActivity.this,
                            SignInActivity.class));
                }

                finish();
            }
        };

        handler.postDelayed(runnable,SPLASH_TIMEOUT);
    }
}
