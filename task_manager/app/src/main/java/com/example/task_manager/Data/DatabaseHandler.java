package com.example.task_manager.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    public DatabaseHandler(Context context){
        super(context,"FIRST_RUN",null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE =
                "CREATE TABLE first_run (\n" +
                "    firstrun TEXT,\n" +
                "    true_false BOOLEAN\n" +
                ");";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS first_run");
        onCreate(sqLiteDatabase);
    }

    public void add_info(String colomn_name, boolean result){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(colomn_name,result);

        db.insert("first_run", null, contentValues);
        db.close();

    }

}
