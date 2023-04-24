package com.example.task_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;

import com.example.task_manager.db.MyDbManager;

import java.util.ArrayList;

public class Upcoming extends AppCompatActivity {

    private MyDbManager myDbManager;
    ListView listViewData;
    ArrayAdapter<String> adapter;
    ArrayList<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncoming);
        myDbManager = new MyDbManager(this);
        myDbManager.openDb();
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

        // Добавляем обработчик нажатия на элемент списка
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Получаем текст элемента, который нужно удалить
                String item = adapter.getItem(position);
                // Удаляем элемент из списка
                adapter.remove(item);
                // Уведомляем адаптер об изменении данных
                adapter.notifyDataSetChanged();
            }
        });
    }

    // переход на выполненные задачи
    public void toCompleted(View view){
        Intent intent = new Intent(this, Completed.class);
        startActivity(intent);
    }
}