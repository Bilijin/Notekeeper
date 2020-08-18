package com.example.notekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_TITLE_EXTRA = "com.example.notekeeper.Note Title";
    public static final String NOTE_TEXT_EXTRA = "com.example.notekeeper.Note Text";
    public static final String NOTE_DATE_EXTRA = "com.example.notekeeper.Note Date";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(new NoteFragment());
    }




    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .commit();
            return true;
        }
        return false;
    }
}