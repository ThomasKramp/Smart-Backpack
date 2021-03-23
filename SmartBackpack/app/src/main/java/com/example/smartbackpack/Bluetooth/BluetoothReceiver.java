package com.example.smartbackpack.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BluetoothReceiver extends BroadcastReceiver {
    private BluetoothReceiverListener listener;

    public BluetoothReceiver() {}

    public BluetoothReceiver(BluetoothReceiverListener listener) {
        this.listener = listener;
    }

    @Override
    /**
     * Receives certain Bluetooth broadcast intents.
     */
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch (state) {
                case BluetoothAdapter.STATE_OFF:
                    showToast(context, BluetoothFragment.BT_OFF_MESSAGE);
                    listener.updateBluetoothSwitch(false);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    break;
                case BluetoothAdapter.STATE_ON:
                    showToast(context, BluetoothFragment.BT_ON_MESSAGE);
                    listener.updateBluetoothSwitch(true);
                    break;
                case BluetoothAdapter.STATE_TURNING_ON:
                    break;
                case BluetoothAdapter.ERROR:
                    showToast(context, "An error has occurred");
                    break;
            }
        }
    }

    private void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
