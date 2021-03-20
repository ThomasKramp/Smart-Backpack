package com.example.smartbackpack.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbackpack.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {

    ArrayList<ListItem> items;
    LayoutInflater mInflater;
    OnListItemListener mListItemListener;

    public ListAdapter(Context listActivity, ArrayList<ListItem> items, OnListItemListener listItemListener) {
        mInflater = LayoutInflater.from(listActivity);
        this.items = items;
        mListItemListener = listItemListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = mInflater.inflate(R.layout.list_item_layout, parent, false);
        return new ListViewHolder(viewHolder, mListItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListItem currentItem = items.get(position);
        holder.mNameView.setText(currentItem.getName());
        holder.mAmountView.setText(String.valueOf(currentItem.getAmount()));
        if (currentItem.getImage() == null)
            holder.mImageView.setImageResource(R.drawable.ic_no_image);
        else holder.mImageView.setImageBitmap(currentItem.getImage());
    }

    @Override
    public int getItemCount() { return items.size(); }
}
