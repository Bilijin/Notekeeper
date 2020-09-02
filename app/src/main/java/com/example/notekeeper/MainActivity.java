package com.example.notekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    public static final String NOTE_TITLE_EXTRA = "com.example.notekeeper.Title";
    public static final String NOTE_TEXT_EXTRA = "com.example.notekeeper.Text";
    public static final String NOTE_DATE_EXTRA = "com.example.notekeeper.Date";
    public static final String NOTE_FRAGMENT_EXTRA = "com.example.notekeeper.FRAG";

    public enum FragmentToLaunch{ EDIT, VIEW };

    BottomNavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(this);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new NoteFragment())
                .commit();


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.home:
                fragment = new NoteFragment();
                break;

            case R.id.settings:
                fragment = new SettingsFragment();
                break;
        }
        return loadFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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