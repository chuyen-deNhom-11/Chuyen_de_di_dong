package com.example.foodonline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.foodonline.Admin.HomeAdminActivity;
import com.example.foodonline.utils.SharePreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SharePreferenceUtils.getUserInfo(SplashActivity.this) == null) {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, HomeAdminActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        }, 1000);
    }
}