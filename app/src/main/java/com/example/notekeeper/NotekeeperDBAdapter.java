package com.example.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NotekeeperDBAdapter {
    private static final String DATABASE_NAME = "name";
    private static final int DATABASE_VERSION = 1;

    public static final String NOTE_TABLE = "snippet";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE = "date";

    private String[] allColumns = { COLUMN_TITLE, COLUMN_TEXT};

    public static final String CREATE_TABLE_NOTE = "create table " + NOTE_TABLE + " ( "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_TEXT + " text not null, "
            + COLUMN_DATE + ");";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private NoteKeeperDBHelper noteKeeperDBHelper;

    public NotekeeperDBAdapter(Context contxt) {
        context = contxt;
    }

    public NotekeeperDBAdapter open() throws android.database.SQLException {
        noteKeeperDBHelper = new NoteKeeperDBHelper(context);
        sqLiteDatabase = noteKeeperDBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        noteKeeperDBHelper.close();
    }

    private static class NoteKeeperDBHelper extends SQLiteOpenHelper{

        NoteKeeperDBHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //this creates the note table
            db.execSQL(CREATE_TABLE_NOTE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(NoteKeeperDBHelper.class.getName(),
                    "Ugraging Database from " + oldVersion + " to " + newVersion +
                    " which will probably destroy all existing data");
            db.execSQL("DROP TABLE IF EXISTS " + NOTE_TABLE);
            onCreate(db);
        }
    }
}
