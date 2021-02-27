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

    String[] vItemNames;
    int[] vItemAmounts;

    LayoutInflater mInflater;

    public ListAdapter(ListActivity listActivity, String[] itemNames, int[] itemAmounts) {
        mInflater = LayoutInflater.from(listActivity);
        vItemNames = itemNames;
        vItemAmounts = itemAmounts;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = mInflater.inflate(R.layout.list_item_layout, parent, false);
        return new ListViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        holder.mNameView.setText(vItemNames[position]);
        holder.mAmountView.setText(String.valueOf(vItemAmounts[position]));
        holder.mImageView.setImageResource(R.drawable.ic_launcher_foreground);
    }

    @Override
    public int getItemCount() { return vItemAmounts.length; }
}
