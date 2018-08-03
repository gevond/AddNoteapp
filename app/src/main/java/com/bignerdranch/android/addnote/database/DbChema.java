package com.bignerdranch.android.addnote.database;

/**
 * Created by User on 12/5/2017.
 */

public class DbChema {

    public static final class NoteTable{
      public static final String NAME = "notes";
      public static final class Cols{
          public static final String UUID = "uuid";
          public static final String TITLE = "title";
          public static final String TEXT = "text";
          public static final String DATE = "date";
          public static final String DATE_NOTIFICATION = "datenotification";
          public static final String ALARM = "alarm";
          public static final String COLOR = "color";
      }
    };

}
