package com.example.smartbackpack.Bluetooth;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartbackpack.R;

import java.util.ArrayList;


public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    ArrayList<DeviceItem> devices;
    LayoutInflater mInflater;
    DeviceItemListener mDeviceItemListener;

    public DeviceAdapter(Context homeActivity, ArrayList<DeviceItem> devices, DeviceItemListener deviceItemListener) {
        mInflater = LayoutInflater.from(homeActivity);
        this.devices = devices;
        mDeviceItemListener =deviceItemListener;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = mInflater.inflate(R.layout.device_item_layout, parent, false);
        return new DeviceViewHolder(viewHolder, mDeviceItemListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        DeviceItem currentItem = devices.get(position);
        holder.mNameView.setText(currentItem.getName());
        holder.mAddressView.setText(currentItem.getMacAddress());
    }

    @Override
    public int getItemCount() { return devices.size(); }
}
