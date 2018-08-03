package com.bignerdranch.android.addnote.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 12/9/2017.
 */

public class AddnoteDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "addnote.db";
    private static final int DATABASE_VERSION = 1;

    public AddnoteDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DbChema.NoteTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                DbChema.NoteTable.Cols.UUID + ", " +
                DbChema.NoteTable.Cols.TITLE + ", " +
                DbChema.NoteTable.Cols.TEXT + ", " +
                DbChema.NoteTable.Cols.DATE +","+
                DbChema.NoteTable.Cols.DATE_NOTIFICATION+","+
                DbChema.NoteTable.Cols.ALARM+","+
                DbChema.NoteTable.Cols.COLOR+
                ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
