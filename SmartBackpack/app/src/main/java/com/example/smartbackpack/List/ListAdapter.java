package com.example.smartbackpack.List;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbackpack.ListActivity;
import com.example.smartbackpack.R;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    private ListItem[] items;

    LayoutInflater mInflater;

    public ListAdapter(ListActivity listActivity, ListItem[] items) {
        mInflater = LayoutInflater.from(listActivity);
        this.items = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = mInflater.inflate(R.layout.list_item_layout, parent, false);
        return new ListViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.mNameView.setText(items[position].getName());
        holder.mAmountView.setText(String.valueOf(items[position].getAmount()));
        holder.mImageView.setImageResource(items[position].getImageResource());
    }

    @Override
    public int getItemCount() { return items.length; }
}
