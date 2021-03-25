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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
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
    ArrayList listPairedDevices;
    TextView btSelected;
    Button btShowPairedDevicesButton;
    Button btConnectButton;

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
        listViewPairedDevices.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

        btShowPairedDevicesButton = view.findViewById(R.id.bt_paired_devices_button);

        btSelected = view.findViewById(R.id.bt_text_view);

        btConnectButton = view.findViewById(R.id.bt_connect_button);

        if (btAdapter != null) {
            if (btAdapter.isEnabled()) {
                btSwitch.setChecked(true);
                showToast(BT_ON_MESSAGE);
                enableOptions(true);
            } else {
                btSwitch.setChecked(false);
                enableOptions(false);
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
                    enableOptions(true);

                } else {
                    disableBluetooth();
                    showToast(BT_OFF_MESSAGE);
                    enableOptions(false);
                }
            }
        });


        btShowPairedDevicesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pairedDevices = btAdapter.getBondedDevices();

                listPairedDevices = new ArrayList<>();

                if (pairedDevices.size() > 0) {
                    Log.d(TAG, "start getting paired devices!");
                    for (BluetoothDevice device: pairedDevices) {
                        String deviceName = device.getName();
                        String deviceMAC = device.getAddress();
                        listPairedDevices.add("Name: " + deviceName + "MAC Address: " + deviceMAC);
                        Log.d(TAG, "Name: " + deviceName + ", MAC Address: " + deviceMAC);
                    }
                    showToast("Showing paired devices");
                } else {
                    showToast("No paired devices");
                }

                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listPairedDevices);
                listViewPairedDevices.setAdapter(adapter);
            }
        });

        listViewPairedDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Selected item
                Log.d(TAG, "Selected " + listPairedDevices.get(position));
                btSelected.setText("Paired device: \n" + (CharSequence) listPairedDevices.get(position));
            }
        });

        btConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Connect to " + btSelected.getText());
            }
        });
    }

    private void enableOptions(boolean show) {
        btShowPairedDevicesButton.setEnabled(show);
        btSelected.setEnabled(show);
        btConnectButton.setEnabled(show);
        listViewPairedDevices.setEnabled(show);
/*        if (show) {
            Log.d(TAG, "show the options");
            btShowPairedDevicesButton.setEnabled(false);
            btConnectButton.setEnabled(false);
        } else {
            Log.d(TAG, "hide the options");
        }*/

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
}

/*
class ConnectThread extends Thread {
    private static final String TAG = "ConnectThread";
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket
        // because mmSocket is final.
        BluetoothSocket tmp = null;
        mmDevice = device;

        try {
            // Get a BluetoothSocket to connect with the given BluetoothDevice.
            // MY_UUID is the app's UUID string, also used in the server code.
            tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
            Log.e(TAG, "Socket's create() method failed", e);
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it otherwise slows down the connection.
        bluetoothAdapter.cancelDiscovery();

        try {
            // Connect to the remote device through the socket. This call blocks
            // until it succeeds or throws an exception.
            mmSocket.connect();
        } catch (IOException connectException) {
            // Unable to connect; close the socket and return.
            try {
                mmSocket.close();
            } catch (IOException closeException) {
                Log.e(TAG, "Could not close the client socket", closeException);
            }
            return;
        }

        // The connection attempt succeeded. Perform work associated with
        // the connection in a separate thread.
        manageMyConnectedSocket(mmSocket);
    }

    // Closes the client socket and causes the thread to finish.
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not close the client socket", e);
        }
    }
}*/
