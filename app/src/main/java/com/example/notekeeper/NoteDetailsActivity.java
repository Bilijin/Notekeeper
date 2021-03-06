package com.example.notekeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

public class NoteDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);

        if (savedInstanceState != null) {
            NoteEditFragment editFragment = (NoteEditFragment) getSupportFragmentManager().findFragmentByTag("Note Edit");
        } else {

            if (getIntent().getSerializableExtra("Fragment") == MainActivity.FragmentToLaunch.VIEW) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.note_container, new NoteDetailFragment())
                        .commit();
            } else if (getIntent().getSerializableExtra("Fragment") == MainActivity.FragmentToLaunch.EDIT) {
                Bundle bundle = new Bundle();
                Intent intent = getIntent();

                bundle.putString("title", intent.getStringExtra("Note Title"));
                bundle.putString("note", intent.getStringExtra("Note Text"));

                NoteEditFragment noteEditFragment = new NoteEditFragment();
                noteEditFragment.setArguments(bundle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.note_container, noteEditFragment, "Note Edit")
                        .commit();
            }
        }

    }

    public void loadFragment(Fragment fragment, String tag) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.note_container,fragment,tag)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", "Edit");
    }
}