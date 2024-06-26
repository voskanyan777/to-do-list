package com.example.task_manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.task_manager.db.MyDbManager;

import java.util.ArrayList;

public class Upcoming extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private MyDbManager myDbManager;
    ListView listViewData;
    ArrayAdapter<String> adapter;
    ArrayAdapter<CharSequence> adapter1;
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Integer> list_id = new ArrayList<Integer>();
    Spinner spinner;
    // Объявление объекта SharedPreferences
    SharedPreferences sharedPreferences;

    // Объявление ключа для сохранения значения Spinner
    private static final String KEY_SPINNER = "spinnerValue";
    String selectedItem;
    private boolean first_spinner = false;
    private boolean second_spinner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uncoming);
        spinner = findViewById(R.id.sort_spinner);
        adapter1 = ArrayAdapter.createFromResource(this, R.array.sort_option, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter1);
        spinner.setOnItemSelectedListener(this);

        myDbManager = new MyDbManager(this);
        myDbManager.openDb();


        listViewData = findViewById(R.id.listView_data);

        // Инициализация объекта SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        // Получение сохраненного значения Spinner из SharedPreferences
        String savedValue = sharedPreferences.getString(KEY_SPINNER, "");
        // Установка сохраненного значения в Spinner
        int spinnerPosition = adapter1.getPosition(savedValue);
        spinner.setSelection(spinnerPosition);

        try {

            selectedItem = spinner.getSelectedItem().toString();
            if (selectedItem.equals("Сортировка: Важные -> Неважные") && selectedItem != null) {
                list = myDbManager.getFromDb("d");
                list_id = myDbManager.getIdFromDb("d");
                first_spinner = true;
            } else if (!selectedItem.equals("Сортировка: Важные -> Неважные") && selectedItem != null) {
                list = myDbManager.getFromDb("n");
                list_id = myDbManager.getIdFromDb("n");
                second_spinner = true;
            } else if (selectedItem == null) {
                list = myDbManager.getFromDb("d");
                list_id = myDbManager.getIdFromDb("d");

            }
        } catch (Exception e) {
            list = myDbManager.getFromDb("d");
            list_id = myDbManager.getIdFromDb("d");
        }
        System.out.println(list_id);

        //Создание списка с чекбоксами. `simple_list_item_checked` -> параметр для создание чекбокса
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, list) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                CheckedTextView textView = (CheckedTextView) super.getView(position, convertView, parent);
                textView.setCheckMarkDrawable(android.R.drawable.checkbox_off_background);

                return textView;
            }
        };

        listViewData.setAdapter(adapter);

        listViewData.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //                System.out.println(list.get(i));
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Меню");
                builder.setMessage("Выберите действие");
                builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Upcoming.this, add_activity.class);
                        intent.putExtra("Id", list_id.get(i));
                        intent.putExtra("Title", list.get(i));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Удалить", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDbManager.deleteData(list.get(i));
                        String toast_text = "Элемент `" + list.get(i) + "` был удален";
                        Toast toast = Toast.makeText(getApplicationContext(), toast_text, Toast.LENGTH_SHORT);
                        toast.show();

                        startActivity(new Intent(Upcoming.this, Upcoming.class));
                        dialog.cancel();
                    }
                });
                builder.show();
                return true;
            }
        });

        // Добавляем обработчик нажатия на элемент списка
        listViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Получаем текст элемента, который нужно удалить
                String item = adapter.getItem(position);
                // Удаляем элемент из списка
                adapter.remove(item);
                myDbManager.insertToCompletedDb(item);
                myDbManager.deleteData(item);
                Toast toast = Toast.makeText(getApplicationContext(), "Задача выполнена! \uD83D\uDE00", Toast.LENGTH_SHORT);
                toast.show();
                // Уведомляем адаптер об изменении данных
                adapter.notifyDataSetChanged();
            }
        });

    }


    // переход на выполненные задачи
    public void toCompleted(View view) {
        Intent intent = new Intent(this, Completed.class);
        startActivity(intent);
    }

    //Нажатие на кнопку Плюс
    public void plus_button(View view) {
        Intent intent = new Intent(this, add_activity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDbManager.closeDb();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // Сохранение выбранного значения Spinner в SharedPreferences
        String selectedValue = adapterView.getItemAtPosition(i).toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SPINNER, selectedValue);
        editor.apply();
        if (first_spinner && i == 1 && list.size() != 0) {
            first_spinner = false;
            Toast toast = Toast.makeText(getApplicationContext(), "Задачи были отсортированы!", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(this, Upcoming.class));
        }
        if (second_spinner && i == 0 && list.size() != 0) {
            second_spinner = false;
            Toast toast = Toast.makeText(getApplicationContext(), "Задачи были отсортированы!", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(this, Upcoming.class));
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}