package com.bignerdranch.android.addnote;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bignerdranch.android.addnote.database.AddnoteDbHelper;
import com.bignerdranch.android.addnote.database.DbChema;
import com.bignerdranch.android.addnote.database.NoteCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by User on 11/30/2017.
 */

public class AddnoteLab {

    private static AddnoteLab sAddnoteLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private AddnoteLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new AddnoteDbHelper(context).getWritableDatabase();

    }

    public static AddnoteLab get(Context context){
        if(sAddnoteLab == null){
            sAddnoteLab = new AddnoteLab(context);
        }
        return sAddnoteLab;
    }

    public  List<Note> getNotes(){
        ArrayList<Note> notes = new ArrayList<>();

        NoteCursorWrapper cursor = queryCursor(null,null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                notes.add(cursor.getNote());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }

        return notes;
    }

    public  void addNote(Note note){
        ContentValues values = getContentValues(note);
        mDatabase.insert(DbChema.NoteTable.NAME,null,values);
    }

    public  Note getNoteById(UUID id){
        NoteCursorWrapper cursor = queryCursor(DbChema.NoteTable.Cols.UUID+"=?",
                new String[] {id.toString()}
                );
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getNote();
        } finally {
            cursor.close();
        }
    }

    public void updateNote(Note note){
        String uuidString = note.getId().toString();
        ContentValues values = getContentValues(note);
        mDatabase.update(DbChema.NoteTable.NAME,values,DbChema.NoteTable.Cols.UUID+" =?",new String[]{uuidString});
    }

    public void removeNote(UUID id){
        String idString = id.toString();
        mDatabase.delete(DbChema.NoteTable.NAME,DbChema.NoteTable.Cols.UUID+"=?",new String[]{idString});
    }

    public void updateAllNotes(List<Note> notes){
        for(Note n:notes){
            removeNote(n.getId());
        }
        for(Note n:notes){
            addNote(n);
        }
    }

    private NoteCursorWrapper queryCursor(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(DbChema.NoteTable.NAME,null,whereClause,whereArgs,null,null,null);
        return new NoteCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Note note){
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbChema.NoteTable.Cols.UUID,note.getId().toString());
        contentValues.put(DbChema.NoteTable.Cols.TITLE,note.getTitle());
        contentValues.put(DbChema.NoteTable.Cols.TEXT,note.getNote());
        contentValues.put(DbChema.NoteTable.Cols.DATE,note.getDate().getTime());
        contentValues.put(DbChema.NoteTable.Cols.DATE_NOTIFICATION,note.getNotDateNotification().getTime());
        contentValues.put(DbChema.NoteTable.Cols.ALARM,note.isAlarm() ? 1 : 0);
        contentValues.put(DbChema.NoteTable.Cols.COLOR,note.getColor());

        return contentValues;
    }

}
