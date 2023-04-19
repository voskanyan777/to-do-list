package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Upcoming extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncoming);
    }

    // переход на выполненные задачи
    public void toCompleted(View view){
            Intent intent = new Intent(this, Completed.class);
            startActivity(intent);
    }
}