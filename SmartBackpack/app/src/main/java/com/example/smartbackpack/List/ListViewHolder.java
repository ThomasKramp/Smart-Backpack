package com.example.smartbackpack.List;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbackpack.R;

public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mNameView;
    TextView mAmountView;
    ImageView mImageView;
    OnListItemListener mListItemListener;

    public ListViewHolder(@NonNull View itemView, OnListItemListener listItemListener) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.list_item_name);
        mAmountView = itemView.findViewById(R.id.list_item_amount);
        mImageView = itemView.findViewById(R.id.list_item_image);
        this.mListItemListener = listItemListener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListItemListener.onItemClick(getLayoutPosition());
    }
}