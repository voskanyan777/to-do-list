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
    int id;
    String title;
    private MyDbManager myDbManager;

    public static boolean input_valudation(String input){
        try{
            int result = Integer.parseInt(input);
            if (result >=0 && result <= 100) {
                return true;
            }
            return false;
        }
        catch (Exception e){
            return false;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        title = intent.getStringExtra("Title");
        id = intent.getIntExtra("Id",-1);



        entertext = findViewById(R.id.enter_text);
        sorttext = findViewById(R.id.sort_text);
        text = findViewById(R.id.error_text);
        entertext.setText(title);
        if(id != -1) {
            sorttext.setText(String.valueOf(id));
        }

        myDbManager = new MyDbManager(this);
        prefs1 = getSharedPreferences("com.example.task_manager", MODE_PRIVATE);
        myDbManager.openDb();
        if (prefs1.getBoolean("firstrun_add_activity", true)) {
            builder = new AlertDialog.Builder(this);
            builder.setTitle("Инструкция");
            String instruction =
                    "Здесь вы можете добавлять свои задачи.\n" +
                    "В первом поле вы вводите название задачи, а во втором его важность.\n" +
                            "Важность задачи записывется в виде целого числа от 0 до 100.\n" +
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
            text.setText("Заполните корректно поле `Важность`!");
            return;
        }
        //Не работает
//        if(entertext.getText().toString().equals(title) && sorttext.getText().toString().equals(String.valueOf(id))){
//            startActivity(new Intent(this,Upcoming.class));
//        }
        if (title != null && id != -1){
            myDbManager.deleteData(title);
            myDbManager.insertToDb(entertext.getText().toString(),Integer.parseInt(sorttext.getText().toString()));
            myDbManager.closeDb();
            toast = Toast.makeText(getApplicationContext(),"Задача изменена!",Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this,Upcoming.class);
            startActivity(intent);
        }
        else {
            myDbManager.insertToDb(entertext.getText().toString(), Integer.parseInt(sorttext.getText().toString()));
            myDbManager.closeDb();
            toast = Toast.makeText(getApplicationContext(), "Задача добавлена!", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(this, Upcoming.class);
            startActivity(intent);
        }
    }


    public void clear(View view){
        entertext.setText("");
        sorttext.setText("");
    }
}