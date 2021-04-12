package com.example.smartbackpack.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbackpack.MainActivity;
import com.example.smartbackpack.R;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class BluetoothFragment extends Fragment implements DeviceItemListener{
    private static final String TAG = "BluetoothFragment";
    public static final String BT_ON_MESSAGE = "Bluetooth enabled";
    public static final String BT_OFF_MESSAGE = "Bluetooth disabled";

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices;

    public static Switch mBluetoothSwitch;
    Button mShowDevicesButton;
    Button mConnectButton;
    TextView mPairedDeviceText;

    ArrayList<DeviceItem> devices = new ArrayList<>();
    DeviceAdapter mDeviceAdapter;
    RecyclerView mRecyclerView;
    public DeviceItem selectedDevice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bluetooth, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.device_scroller);
        mDeviceAdapter = new DeviceAdapter(getContext(), devices, this);
        mRecyclerView.setAdapter(mDeviceAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mShowDevicesButton = view.findViewById(R.id.show_devices_button);
        mConnectButton = view.findViewById(R.id.connect_button);
        mPairedDeviceText = view.findViewById(R.id.paired_devices_text);

        mBluetoothSwitch = view.findViewById(R.id.bluetooth_switch);
        checkToggle();

        // Get Devices
        mShowDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevices = mBluetoothAdapter.getBondedDevices();
                if (pairedDevices.size() > 0) {
                    Log.d(TAG, "start getting paired devices!");
                    for (BluetoothDevice device: pairedDevices) {
                        DeviceItem deviceItem = new DeviceItem(device.getName(), device.getAddress());
                        if (!devices.contains(deviceItem)){
                            devices.add(deviceItem);
                            Log.d(TAG, "Name: " + deviceItem.getName() + ", MAC Address: " + deviceItem.getMacAddress());
                        }
                    }
                    mDeviceAdapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "Showing paired devices", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "No paired devices", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Connect to bluetooth
        mConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDevice != null){
                    MainActivity.StartBluetoothTask(mBluetoothAdapter, selectedDevice.getMacAddress());
                    Log.d(TAG, "Connect to " + selectedDevice.getName());
                    Log.d(TAG, "Address: " + selectedDevice.getMacAddress());
                } else Toast.makeText(getActivity(), "No device selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkToggle() {
        if (mBluetoothAdapter != null) {
            if (mBluetoothAdapter.isEnabled()) {
                mBluetoothSwitch.setChecked(true);
                Toast.makeText(getActivity(), BT_ON_MESSAGE, Toast.LENGTH_SHORT).show();
                enableOptions(View.VISIBLE);
            } else {
                mBluetoothSwitch.setChecked(false);
                enableOptions(View.INVISIBLE);
            }
        } else {
            Toast.makeText(getActivity(), "Device does not support bluetooth", Toast.LENGTH_SHORT).show();
        }
        // If user toggles the bt switch
        mBluetoothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBluetoothAdapter.enable();
                    Toast.makeText(getActivity(), BT_ON_MESSAGE, Toast.LENGTH_SHORT).show();
                    enableOptions(View.VISIBLE);
                } else {
                    mBluetoothAdapter.disable();
                    Toast.makeText(getActivity(), BT_OFF_MESSAGE, Toast.LENGTH_SHORT).show();
                    enableOptions(View.INVISIBLE);
                }
            }
        });

    }

    private void enableOptions(int visibility) {
        mShowDevicesButton.setVisibility(visibility);
        mPairedDeviceText.setVisibility(visibility);
        mConnectButton.setVisibility(visibility);
        mRecyclerView.setVisibility(visibility);
    }

    @Override
    public void onDeviceClick(int position) {
        // Selected item
        selectedDevice = devices.get(position);
        Log.d(TAG, "Selected: " + selectedDevice.getName());
        mPairedDeviceText.setText("Paired device: \n" + selectedDevice.getName() + "\n" + selectedDevice.getMacAddress());
    }
}
