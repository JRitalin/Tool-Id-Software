package com.example.tool_identification.Startup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tool_identification.MainActivity;
import com.example.tool_identification.R;

public class WelcomeSplash extends AppCompatActivity {
    private static final int SPLASH_TIME_OUT = 2500;//Milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_splash);
        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent (WelcomeSplash.this, MainActivity.class);
            startActivity(homeIntent);
            overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            finish();
        },SPLASH_TIME_OUT);
    }
}