package com.example.smartbackpack.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.smartbackpack.R;

public class BluetoothFragment extends Fragment {
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    public static Switch btSwitch;
    public static String BT_ON_MESSAGE = "Bluetooth enabled";
    public static String BT_OFF_MESSAGE = "Bluetooth disabled";

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
        btSwitch = view.findViewById(R.id.bt_switch);

        if (btAdapter != null) {
            if (btAdapter.isEnabled()) {
                btSwitch.setChecked(true);
                showToast(BT_ON_MESSAGE);
            } else {
                btSwitch.setChecked(false);
            }
        } else {
            showToast("Device does not support bluetooth");
        }

        // If user toggles the bt switch
        btSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableBluetooth();
                    showToast(BT_ON_MESSAGE);
                } else {
                    disableBluetooth();
                    showToast(BT_OFF_MESSAGE);
                }
            }
        });
    }

    private void disableBluetooth() {
        btAdapter.disable();
    }

    private void enableBluetooth() {
        btAdapter.enable();
    }

    private void showToast(String message){
        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
    }
}