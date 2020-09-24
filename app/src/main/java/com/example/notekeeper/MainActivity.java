package com.example.notekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity{

    public enum FragmentToLaunch{ EDIT, VIEW }

    String userName;
    boolean darkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadPreferences();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container,new HomeFragment())
                .commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public String getUserName() {
        return "Hello " + userName + " :)";
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
//        boolean isDarkMode = sharedPreferences.getBoolean("dark_mode", false);

//        if (isDarkMode) {
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//        } else{
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//        }
//        darkMode = isDarkMode;
        userName = sharedPreferences.getString("signature","User");
    }

    public boolean loadFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
                    .addToBackStack(tag)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void openSettings(MenuItem item) {
        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
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