package com.example.smartbackpack.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.example.smartbackpack.R;

import java.util.ArrayList;
import java.util.Set;

public class BluetoothFragment extends Fragment {
    private static final String TAG = "BluetoothFragment";
    private BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
    private Set<BluetoothDevice> pairedDevices;

    public static Switch btSwitch;
    public static String BT_ON_MESSAGE = "Bluetooth enabled";
    public static String BT_OFF_MESSAGE = "Bluetooth disabled";
    public ListView listViewPairedDevices;

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
        listViewPairedDevices = view.findViewById(R.id.bt_list_view);

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

        Button btShowPairedDevices = view.findViewById(R.id.bt_paired_devices);
        btShowPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevices = btAdapter.getBondedDevices();

                ArrayList listPairedDevices = new ArrayList<>();

                if (pairedDevices.size() > 0) {
                    Log.d(TAG, "start getting paired devices!");
                    for (BluetoothDevice device: pairedDevices) {
                        String deviceName = device.getName();
                        String deviceMAC = device.getAddress();
                        listPairedDevices.add("Name: " + deviceName + "MAC Address: " + deviceMAC);
                        Log.d(TAG, "Name: " + deviceName + ", MAC Address: " + deviceMAC);
                    }
                }
                showToast("Showing paired devices");

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listPairedDevices);
                listViewPairedDevices.setAdapter(adapter);
            }
        });


    }

    private void disableBluetooth() {
        btAdapter.disable();
    }

    private void enableBluetooth() {
        btAdapter.enable();
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

/*    public void showList(View v) {
*//*        pairedDevices = btAdapter.getBondedDevices();

        ArrayList list = new ArrayList();

        if (pairedDevices.size() > 0) {
            Log.d("BT FRAGMENT", "start getting paired devices!");
            for (BluetoothDevice device: pairedDevices) {
                String deviceName = device.getName();
                String deviceMAC = device.getAddress();
                list.add("Name: " + deviceName + "MAC Address: " + deviceMAC);
                Log.d("BT FRAGMENT", "Name: " + deviceName + "MAC Address: " + deviceMAC);
            }
        }
        showToast("Showing paired devices");

        ListView btListView = (ListView) v.findViewById(R.id.bt_listview);
        //final ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_item_layout, list);*//*


    }*/
}