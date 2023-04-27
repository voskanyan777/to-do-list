package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_manager.db.MyDbManager;

public class add_activity extends AppCompatActivity {

    Toast toast;
    EditText entertext;
    TextView text;
    private MyDbManager myDbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        entertext = findViewById(R.id.enter_text);
        text = findViewById(R.id.error_text);
        myDbManager = new MyDbManager(this);
        myDbManager.openDb();
    }
    public void save(View view){
        //entertext.getText();
        if(entertext.getText().toString().equals("")){
            text.setText("Поле не должно быть пустым!");
        }
        else{
            myDbManager.insertToDb(entertext.getText().toString());
            myDbManager.closeDb();
            toast = Toast.makeText(getApplicationContext(), "Задача добавлена", Toast.LENGTH_SHORT);toast.show();
            Intent intent = new Intent(this,Upcoming.class);
            startActivity(intent);
        }


    }

    public void clear(View view){
        entertext.setText("");
    }
}