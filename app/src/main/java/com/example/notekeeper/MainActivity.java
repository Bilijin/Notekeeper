package com.example.notekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String NOTE_TITLE_EXTRA = "com.example.notekeeper.Title";
    public static final String NOTE_TEXT_EXTRA = "com.example.notekeeper.Text";
    public static final String NOTE_DATE_EXTRA = "com.example.notekeeper.Date";
    public static final String NOTE_FRAGMENT_EXTRA = "com.example.notekeeper.FRAG";

    public enum FragmentToLaunch{ EDIT, VIEW };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new NoteFragment())
                .commit();
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

    public void itemMenu(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.edit:
//                NoteFragment noteFragment = new NoteFragment();
//                noteFragment.viewNoteDetails();
//                break;
//
//            case R.id.delete:
//                loadFragment(new HomeFragment());
//                break;
//        }
    }


}