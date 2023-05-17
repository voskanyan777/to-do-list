package com.example.task_manager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class logoactivity extends AppCompatActivity {
    private static final long DELAY_TIME = 1750; // 2 seconds delay

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logo_activity);

        // Delay opening MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(logoactivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Finish LogoActivity
            }
        }, DELAY_TIME);
    }
}
