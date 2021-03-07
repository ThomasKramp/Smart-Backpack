package com.example.smartbackpack.List;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbackpack.R;

public class ListViewHolder extends RecyclerView.ViewHolder {

    TextView mNameView;
    TextView mAmountView;
    ImageView mImageView;

    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        mNameView = itemView.findViewById(R.id.list_item_name);
        mAmountView = itemView.findViewById(R.id.list_item_amount);
        mImageView = itemView.findViewById(R.id.list_item_image);
    }
}