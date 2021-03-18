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
    ImageView mDeleteImage;
    OnListItemListener mListItemListener;

    public ListViewHolder(@NonNull View itemView, OnListItemListener listItemListener) {
        super(itemView);

        this.mListItemListener = listItemListener;
        itemView.setOnClickListener(this);

        mNameView = itemView.findViewById(R.id.list_item_name);
        mAmountView = itemView.findViewById(R.id.list_item_amount);
        mImageView = itemView.findViewById(R.id.list_item_image);

        mDeleteImage = itemView.findViewById(R.id.delete_item_image);
        mDeleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { mListItemListener.onDeleteClick(getLayoutPosition()); }
        });
    }

    @Override
    public void onClick(View v) { mListItemListener.onItemClick(getLayoutPosition()); }
}