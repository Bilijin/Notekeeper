package com.example.notekeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FloatingActionButton save;

    private EditText title, text;
    private AlertDialog confirmSaveObject;
    NotekeeperDBAdapter dbAdapter;
    private boolean isNew = false;
    long identify;
    public NoteEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteEditFragment newInstance(String param1, String param2) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View editView =  inflater.inflate(R.layout.fragment_note_edit, container, false);


        title = editView.findViewById(R.id.title_text);
        text = editView.findViewById(R.id.note_body);
        save = editView.findViewById(R.id.save);

        dbAdapter = new NotekeeperDBAdapter(getActivity());

        if (getActivity().getIntent().getStringExtra("fragment").equals("New Note")) {
            isNew = true;
            getActivity().setTitle("New Note");
        } else {
            getActivity().setTitle("Edit Note");
            identify = getActivity().getIntent().getLongExtra("ID", 0);
        }

        if (savedInstanceState != null) {
            title.setText(savedInstanceState.getString("title"));
            text.setText(savedInstanceState.getString("text"));
        } else {

            Bundle b = this.getArguments();
            if (b != null) {
                title.setText(b.getString("title"));
                text.setText(b.getString("note"));
            }
        }

        buildSaveDialog();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmSaveObject.show();
            }
        });
        return editView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("title", title.getText().toString());
        outState.putString("text", text.getText().toString());
    }

    private void buildSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Save Note?");
        builder.setTitle("Are you sure you want to save the note?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                NotekeeperDBAdapter notekeeperDBAdapter = new NotekeeperDBAdapter(getActivity());
                notekeeperDBAdapter.open();

                if (isNew) {
                    addNewNote(notekeeperDBAdapter);
                } else {
                    updateNote(notekeeperDBAdapter);
                }

                notekeeperDBAdapter.close();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        confirmSaveObject = builder.create();
    }

    public void addNewNote(NotekeeperDBAdapter dbAdapter1) {
        String note = text.getText().toString();
        String tp = title.getText().toString();

        if (note.isEmpty() || tp.isEmpty()) {
            Toast.makeText(getActivity(),"Ensure you enter a title and your note",Toast.LENGTH_SHORT).show();
        } else {
            dbAdapter1.createNote(tp, note);
            Toast.makeText(getActivity(), "Your new note has been created", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateNote(NotekeeperDBAdapter dbAdapter1) {
        String note = text.getText().toString();
        String tp = title.getText().toString();

        if (note.isEmpty() || tp.isEmpty()) {
            Toast.makeText(getActivity(),"Ensure you enter a title and your note",Toast.LENGTH_SHORT).show();
        } else {
            dbAdapter1.updateNote(identify, tp, note);
            Toast.makeText(getActivity(), "Note updated sucessfully",Toast.LENGTH_SHORT).show();
        }
    }
}