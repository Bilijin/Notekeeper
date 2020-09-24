package com.example.notekeeper;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notekeeper.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyNoteRecyclerViewAdapter extends RecyclerView.Adapter<MyNoteRecyclerViewAdapter.ViewHolder> {

    private final List<Snippet> snippets;
    private Context context;
    private int position;
    private static RecyclerViewClickListener clickListener;
    private NotekeeperDBAdapter dbAdapter;

    public MyNoteRecyclerViewAdapter(List<Snippet> content, Context context, RecyclerViewClickListener clickListener) {
        snippets = content;
        this.context = context;
        MyNoteRecyclerViewAdapter.clickListener = clickListener;
        dbAdapter = new NotekeeperDBAdapter(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_note_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Snippet snippet = snippets.get(position);
        this.position = position;

        TextView textView = holder.mTitleView;
        textView.setText(snippet.getTitle());

        TextView txtView = holder.mContentView;
        txtView.setText(snippet.getText());

        ImageView bin = holder.mBin;

        bin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbAdapter.open();
                dbAdapter.deleteNote(snippet.getId());
                snippets.clear();
                snippets.addAll(dbAdapter.getAllNotes());

                notifyDataSetChanged();
                dbAdapter.close();
            }
        });

    }

    @Override
    public int getItemCount() {
        return snippets.size();
    }

    public int getPosition() {
        return position;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mContentView;
        public final ImageView mBin;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = view.findViewById(R.id.item_title);
            mContentView = view.findViewById(R.id.content);
            mBin = view.findViewById(R.id.bin);


            view.setOnClickListener(this);
            view.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuInflater inflater = new MenuInflater(v.getContext());
            inflater.inflate(R.menu.note_edit_menu, menu);
        }
    }

}