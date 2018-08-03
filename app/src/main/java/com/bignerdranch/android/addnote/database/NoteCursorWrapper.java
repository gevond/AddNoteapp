package com.bignerdranch.android.addnote.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.bignerdranch.android.addnote.Note;

import java.util.Date;
import java.util.UUID;

/**
 * Created by User on 12/9/2017.
 */

public class NoteCursorWrapper extends CursorWrapper{

    /*
    * String uuidString = getString(getColumnIndex(CrimeDbChema.CrimeTable.Cols.UUID));
        String titleString = getString(getColumnIndex(CrimeDbChema.CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeDbChema.CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeDbChema.CrimeTable.Cols.SOLVED));
        String suspect = getString(getColumnIndex(CrimeDbChema.CrimeTable.Cols.SUSPECT));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setmTitle(titleString);
        crime.setmDate(new Date(date));
        crime.setmSolved(isSolved != 0);
        crime.setmSuspect(suspect);

        return crime;
    * */

    public NoteCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Note getNote(){
        String uuidString = getString(getColumnIndex(DbChema.NoteTable.Cols.UUID));
        String titleString = getString(getColumnIndex(DbChema.NoteTable.Cols.TITLE));
        String textString = getString(getColumnIndex(DbChema.NoteTable.Cols.TEXT));
        long date = getLong(getColumnIndex(DbChema.NoteTable.Cols.DATE));
        long datenotification = getLong(getColumnIndex(DbChema.NoteTable.Cols.DATE_NOTIFICATION));
        int alarm = getInt(getColumnIndex(DbChema.NoteTable.Cols.ALARM));
        String color = getString(getColumnIndex(DbChema.NoteTable.Cols.COLOR));

        Note note = new Note(UUID.fromString(uuidString));
        note.setTitle(titleString);
        note.setNote(textString);
        note.setDate(new Date(date));
        note.setNotDateNotification(new Date(datenotification));
        note.setAlarm(alarm != 0);
        note.setColor(color);

        return note;
    }

}
