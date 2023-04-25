package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.task_manager.db.MyDbManager;

public class add_activity extends AppCompatActivity {

    EditText entertext;
    private MyDbManager myDbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        entertext = findViewById(R.id.enter_text);
        myDbManager = new MyDbManager(this);
        myDbManager.openDb();
    }
    public void save(View view){
        //entertext.getText();
        if(entertext.getText().toString().equals("")){
            String empty_line = "Вы пытаетесь добавить пустую задачу. Не считаете ли вы это странным?\uD83D\uDE10";
            Toast toast = Toast.makeText(getApplicationContext(), empty_line, Toast.LENGTH_LONG);toast.show();
        }
        else{
            myDbManager.insertToDb(entertext.getText().toString());
            myDbManager.closeDb();
            Toast toast = Toast.makeText(getApplicationContext(), "Задача добавлена", Toast.LENGTH_SHORT);toast.show();
            Intent intent = new Intent(this,Upcoming.class);
            startActivity(intent);
        }


    }

    public void clear(View view){
        entertext.setText("");
    }
}