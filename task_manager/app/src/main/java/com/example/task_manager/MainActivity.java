package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    SharedPreferences prefs = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = getSharedPreferences("com.example.task_manager", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstrun", true)) {
            setContentView(R.layout.activity_main);
            prefs.edit().putBoolean("firstrun", false).commit();
        }
        else{
            setContentView(R.layout.activity_uncoming);
            Intent intent = new Intent(this, Upcoming.class);
            startActivity(intent);
        }
    }
    // переход на главную страницу
    public void toUnCompleted(View view){
        Intent intent = new Intent(this, Upcoming.class);
        startActivity(intent);
    }
    public void toCompleted(View view){
        Intent intent = new Intent(this, Completed.class);
        startActivity(intent);
    }
}