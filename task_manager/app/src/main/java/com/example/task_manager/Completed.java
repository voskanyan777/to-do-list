package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.task_manager.db.MyDbManager;

import java.util.ArrayList;

public class Completed extends AppCompatActivity {
    private MyDbManager myDbManager;

    ListView listViewData;

    ArrayAdapter<String> adapter;

    ArrayList<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complited);
        myDbManager = new MyDbManager(this);
        myDbManager.openDb();

        list = myDbManager.getFromCompletedDb();
        listViewData = findViewById(R.id.listView_data);
        //Создание списка с чекбоксами. `simple_list_item_checked` -> параметр для создание чекбокса
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_checked, list) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                CheckedTextView textView = (CheckedTextView) super.getView(position, convertView, parent);
                textView.setCheckMarkDrawable(android.R.drawable.checkbox_on_background);
                return textView;
            }
        };
        listViewData.setAdapter(adapter);
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Получаем текст элемента, который нужно удалить
                String item = adapter.getItem(position);
                // Удаляем элемент из списка
                adapter.remove(item);
                myDbManager.insertToDb(item);
                myDbManager.deleteCompletedData(item);
                Toast toast = Toast.makeText(getApplicationContext(), "Задача выполнена! \uD83D\uDE00", Toast.LENGTH_SHORT);toast.show();
                // Уведомляем адаптер об изменении данных
                adapter.notifyDataSetChanged();
            }
        });
    }

    // переход на предстоящие задачи
    public void toUpcoming(View view) {
        Intent intent = new Intent(this, Upcoming.class);
        startActivity(intent);
    }
}