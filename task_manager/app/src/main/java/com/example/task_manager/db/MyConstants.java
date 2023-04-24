package com.example.task_manager.db;

public class MyConstants {
    public static final String TABLE_NAME = "todo";
    public static final String _ID = "_id";
    public static final String TITLE = "list";
    public static final String DB_NAME = "database.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT)";

    /*
    * public static final String TABLE_STRUCTURE = "CREATE TABLE IF NOT EXISTS " +
        TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT)";
*/

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
