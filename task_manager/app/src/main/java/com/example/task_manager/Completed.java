package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Completed extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complited);
    }

    // переход на предстоящие задачи
    public void toUpcoming(View view){
        Intent intent = new Intent(this, Upcoming.class);
        startActivity(intent);
    }
}