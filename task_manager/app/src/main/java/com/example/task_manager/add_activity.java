package com.example.task_manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task_manager.db.MyDbManager;

public class add_activity extends AppCompatActivity {

    SharedPreferences prefs1 = null;
    AlertDialog.Builder builder;
    Toast toast;
    EditText entertext;
    EditText sorttext;
    TextView text;
    private MyDbManager myDbManager;

    public static boolean input_valudation(String input){
        try{
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        entertext = findViewById(R.id.enter_text);
        sorttext = findViewById(R.id.sort_text);
        text = findViewById(R.id.error_text);
        myDbManager = new MyDbManager(this);
        prefs1 = getSharedPreferences("com.example.task_manager", MODE_PRIVATE);
        myDbManager.openDb();
        if (prefs1.getBoolean("firstrun_add_activity", true)) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Инструкция");
            String instruction =
                    "Здесь вы можете добавлять свои задачи.\n" +
                    "В первом поле вы вводите название задачи, а во втором его важность.\n" +
                            "Важность задачи записывается в виде целого числа.\n" +
                            "Учтите, что при неправильном вводе программа будет вам сообщать об этом!";
            builder.setMessage(instruction);
            builder.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
            prefs1.edit().putBoolean("firstrun_add_activity", false).commit();

        }
        else{

        }
    }


    public void save(View view){
        if(entertext.getText().toString().equals("") && sorttext.getText().toString().equals("")){
            text.setText("Поля не должны быть пустыми!");
            return;
        }
        if(!entertext.getText().toString().equals("") && sorttext.getText().toString().equals("")){
            text.setText("Поле `Важность` не должна быть пустым!");
            return;
        }
        if(entertext.getText().toString().equals("") && !sorttext.getText().toString().equals("")){
            text.setText("Введите задачу!");
            return;
        }
        if(!entertext.getText().toString().equals("") && !input_valudation(sorttext.getText().toString())){
            text.setText("Поле `Важность` должна содержать целое число!");
            return;
        }
        myDbManager.insertToDb(entertext.getText().toString(),Integer.parseInt(sorttext.getText().toString()));
        myDbManager.closeDb();
        toast = Toast.makeText(getApplicationContext(), "Задача добавлена", Toast.LENGTH_SHORT);
        toast.show();
        Intent intent = new Intent(this,Upcoming.class);
        startActivity(intent);
    }


    public void clear(View view){
        entertext.setText("");
        sorttext.setText("");
    }
}