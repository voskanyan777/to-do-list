package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        * int a = 12;
        super.onCreate(savedInstanceState);
        if (a > 0){
            setContentView(R.layout.activity_uncoming);
        }
        else{
            setContentView(R.layout.activity_main);
        }*/
    }

    // переход на главную страницу
    public void toCompleted(View view){
        Intent intent = new Intent(this, Upcoming.class);
        startActivity(intent);
    }
}