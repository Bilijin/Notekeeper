package com.example.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 */
public class NoteFragment extends Fragment implements RecyclerViewClickListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private List<Snippet> snippets;
    MyNoteRecyclerViewAdapter noteRecyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    FloatingActionButton newNote;
    NotekeeperDBAdapter dbAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NoteFragment() {
    }


    public static NoteFragment newInstance(int columnCount) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_item_list, container, false);
        recyclerView = view.findViewById(R.id.list);
        newNote = view.findViewById(R.id.new_note);
        Context context = view.getContext();
        getActivity().setTitle("Jottings");

        dbAdapter = new NotekeeperDBAdapter(context);
        dbAdapter.open();
        Bundle b = this.getArguments();
        String categoryName = b.getString("category");

        snippets = getSnippets(dbAdapter, categoryName);

        dbAdapter.close();


        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        noteRecyclerViewAdapter = new MyNoteRecyclerViewAdapter(snippets, getActivity(),this);
        recyclerView.setAdapter(noteRecyclerViewAdapter);

        newNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewNote();
            }
        });
        return view;
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        viewNoteDetails(position, MainActivity.FragmentToLaunch.VIEW);
    }

//    public void launch(String vv, int position) {
//        switch (vv){
//            case "view" :
//                viewNoteDetails(, MainActivity.FragmentToLaunch.VIEW);
//                break;
//
//            case "edit":
//
//        }
//    }

    private void viewNoteDetails(int position, MainActivity.FragmentToLaunch ftl) {
        Snippet snippet = snippets.get(position);

        Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);

        intent.putExtra("Note Title", snippet.getTitle());
        intent.putExtra("Note Text", snippet.getText());
        intent.putExtra("Note Date", snippet.getDate());
        intent.putExtra("Note Category", snippet.getCategory());
        intent.putExtra("ID", snippet.getId());

        switch (ftl) {
            case EDIT:
                intent.putExtra("Fragment",MainActivity.FragmentToLaunch.EDIT);
                break;
            case VIEW:
                intent.putExtra("Fragment",MainActivity.FragmentToLaunch.VIEW);
        }

        startActivity(intent);
    }

    private void addNewNote() {
        Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
        intent.putExtra("Fragment",MainActivity.FragmentToLaunch.EDIT);
        intent.putExtra("fragment", "New Note");
        startActivity(intent);
    }

    private ArrayList<Snippet> getSnippets(NotekeeperDBAdapter adapter, String cat) {
        ArrayList<Snippet> notes = new ArrayList<>();
        switch (cat) {
            case "All":
                notes = adapter.getAllNotes();
                break;

            case "General":
                notes = adapter.getGeneralNotes();
                break;

            case "Personal":
                notes = adapter.getPersonalNotes();
                break;

            case "Hobbies":
                notes = adapter.getHobbyNotes();
                break;

            case "Work":
                notes = adapter.getWorkNotes();
                break;

            case "Food":
                notes = adapter.getFoodNotes();
                break;
        }

        return notes;
    }
}