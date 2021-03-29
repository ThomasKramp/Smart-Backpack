package com.example.smartbackpack.Bluetooth;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbackpack.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView mNameView;
    TextView mAddressView;
    DeviceItemListener mDeviceItemListener;

    public DeviceViewHolder(@NonNull View itemView, DeviceItemListener deviceItemListener) {
        super(itemView);
        mDeviceItemListener = deviceItemListener;
        itemView.setOnClickListener(this);

        mNameView = itemView.findViewById(R.id.device_item_name);
        mAddressView = itemView.findViewById(R.id.device_address_text);
    }

    @Override
    public void onClick(View v) { mDeviceItemListener.onDeviceClick(getLayoutPosition());}
}
