package com.example.notekeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.time.LocalDate;

public class NotekeeperDBAdapter {
    private static final String DATABASE_NAME = "notekepeer.db";
    private static final int DATABASE_VERSION = 1;

    public static final String NOTE_TABLE = "snippet";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_DATE = "date";

    private String[] allColumns = { COLUMN_ID, COLUMN_TITLE, COLUMN_TEXT, COLUMN_DATE};

    public static final String CREATE_TABLE_NOTE = "create table " + NOTE_TABLE + " ( "
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_TEXT + " text not null, "
            + COLUMN_DATE + ");";

    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private NoteKeeperDBHelper noteKeeperDBHelper;

    public NotekeeperDBAdapter(Context ctx) {
        this.context = ctx;
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

    public Snippet createNote(String title, String text) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_TEXT,text);
        values.put(COLUMN_DATE, LocalDate.now() + "");

        long insertId = sqLiteDatabase.insert(NOTE_TABLE, null, values);
        Cursor cursor = sqLiteDatabase.query(NOTE_TABLE, allColumns, COLUMN_ID + " = " + insertId, null, null,
                null, null);

        cursor.moveToFirst();
        Snippet newSnippet = cursorToNote(cursor);
        cursor.close();

        return newSnippet;
    }

    public long updateNote(long idNew, String newTitle, String newText) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, newTitle);
        values.put(COLUMN_TEXT,newText);
        values.put(COLUMN_DATE, LocalDate.now() + "");

        return sqLiteDatabase.update(NOTE_TABLE, values, COLUMN_ID + " = " + idNew, null);
    }

    public ArrayList<Snippet> getAllNotes() {
        ArrayList<Snippet> Snippets = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(NOTE_TABLE,allColumns,null, null, null,null, null);

        for (cursor.moveToLast(); !cursor.isBeforeFirst(); cursor.moveToPrevious()) {
            Snippet snippet = cursorToNote(cursor);
            Snippets.add(snippet);
        }
        cursor.close();
        return Snippets;
    }

    private Snippet cursorToNote(Cursor cursor) {

        return new Snippet(cursor.getString( 1 ),
                cursor.getString( 2 ),
                cursor.getString(3),
                cursor.getLong(0));
    }
}
