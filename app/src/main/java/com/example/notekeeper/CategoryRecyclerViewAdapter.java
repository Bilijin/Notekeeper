package com.example.notekeeper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CategoryRecyclerViewAdapter extends RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder> {

    ArrayList<Category> allCategories;
    RecyclerViewClickListener clickListener;

    public CategoryRecyclerViewAdapter(ArrayList<Category> categories, RecyclerViewClickListener listener) {
        this.allCategories = categories;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = allCategories.get(position);

        TextView textView = holder.nameView;
        textView.setText(category.getName());

        ImageView imageView = holder.iconView;
        imageView.setImageResource(category.getImage());
    }

    @Override
    public int getItemCount() {
        return allCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View mView;
        ImageView iconView;
        TextView nameView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            iconView = view.findViewById(R.id.category_icon);
            nameView = view.findViewById(R.id.category_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickListener.recyclerViewListClicked(v, this.getLayoutPosition());
        }
    }
}
