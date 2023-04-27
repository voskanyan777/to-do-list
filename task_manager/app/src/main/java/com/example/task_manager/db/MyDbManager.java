package com.example.task_manager.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context){
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }
    public void openDb(){
        db = myDbHelper.getWritableDatabase();
    }
    public void insertToDb(String title){
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TITLE, title);
        db.insert(MyConstants.TABLE_NAME,null,cv);
    }

    public ArrayList<String> getFromDb(){
        ArrayList<String> tempList = new ArrayList<String>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null,null,null,null,null);

        int titleIndex = cursor.getColumnIndex(MyConstants.TITLE);
        if (titleIndex >= 0) {
            while(cursor.moveToNext()){
                String title = cursor.getString(titleIndex);
                tempList.add(title);
            }
        }
        cursor.close();
        return tempList;
    }
    public void deleteData(String title) {
        db.delete(MyConstants.TABLE_NAME, MyConstants.TITLE + "=?", new String[]{title});
        db.close();
    }
    //Закрытие БД
    public void closeDb(){
        myDbHelper.close();
    }
}
