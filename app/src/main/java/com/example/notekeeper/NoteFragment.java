package com.example.notekeeper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.notekeeper.dummy.DummyContent;

import java.util.ArrayList;
import java.util.Calendar;
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
    Calendar calendar;

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

        calendar = Calendar.getInstance();
        int current_date = calendar.get(Calendar.DATE);

        snippets = new ArrayList<>();
        snippets.add(new Snippet(R.drawable.cappucino,"Cuppa","A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee" +
                "A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee A cuppa coffee", current_date));
        snippets.add(new Snippet(R.drawable.cappucino,"Test","A cuppa coffee", current_date));
        snippets.add(new Snippet(R.drawable.cappucino,"Cuppa","A cuppa coffee", current_date));
        snippets.add(new Snippet(R.drawable.cappucino,"Cuppa","A cuppa coffee", current_date));

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false));

        noteRecyclerViewAdapter = new MyNoteRecyclerViewAdapter(snippets, getActivity(),this);
        recyclerView.setAdapter(noteRecyclerViewAdapter);

        return view;
    }

    @Override
    public void recyclerViewListClicked(View v, int position){
        viewNoteDetails(position);
    }

    private void viewNoteDetails(int position) {
        Snippet snippet = snippets.get(position);

        Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);

        intent.putExtra(MainActivity.NOTE_TITLE_EXTRA, snippet.getTitle());
        intent.putExtra(MainActivity.NOTE_TEXT_EXTRA, snippet.getText());
        intent.putExtra(MainActivity.NOTE_DATE_EXTRA, snippet.getDate());

        startActivity(intent);
    }
}