package com.example.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteDetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    FloatingActionButton button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView date,noteTitle,note;

    public NoteDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NoteDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteDetailFragment newInstance(String param1, String param2) {
        NoteDetailFragment fragment = new NoteDetailFragment();
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

        View noteView = inflater.inflate(R.layout.fragment_note_detail, container, false);
        // Inflate the layout for this fragment

        date = noteView.findViewById(R.id.date);
        noteTitle = noteView.findViewById(R.id.title_text);
        note = noteView.findViewById(R.id.note_body);
        button = noteView.findViewById(R.id.edit);
        Intent intent = getActivity().getIntent();

        date.setText(String.valueOf(intent.getStringExtra(MainActivity.NOTE_DATE_EXTRA)));
        noteTitle.setText(intent.getStringExtra(MainActivity.NOTE_TITLE_EXTRA));
        note.setText(intent.getStringExtra(MainActivity.NOTE_TEXT_EXTRA));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title", noteTitle.getText().toString());
                bundle.putString("note", note.getText().toString());

                NoteEditFragment noteEditFragment = new NoteEditFragment();
                noteEditFragment.setArguments(bundle);

                ((NoteDetailsActivity)getActivity()).loadFragment(noteEditFragment, "Note Edit");
            }
        });

        return noteView;
    }
}