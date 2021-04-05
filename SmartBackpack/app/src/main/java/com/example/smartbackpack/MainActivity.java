package com.example.smartbackpack;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbackpack.Bluetooth.BluetoothFragment;
import com.example.smartbackpack.Bluetooth.BluetoothReceiver;
import com.example.smartbackpack.Bluetooth.BluetoothReceiverListener;
import com.google.android.material.tabs.TabLayout;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BluetoothReceiverListener {
    private static final String TAG = "MainActivity";
    private BluetoothReceiver btReceiver;
    private PagerAdapter adapter;
    public static BluetoothTask bluetoothTask;
    public static String Data = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btReceiver = new BluetoothReceiver(this);

        // Register for broadcasts on BluetoothAdapter state change
        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(btReceiver, filter);

        // Create an instance of the tab layout from the view.
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.TabLabel1));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.TabLabel2));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.TabLabel3));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.TabLabel4));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // Use PagerAdapter to manage page views in fragments.
        // Each page is represented by its own fragment.
        final ViewPager viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(btReceiver);
    }

    @Override
    public void updateBluetoothSwitch(boolean update) {
        // Update switch
        BluetoothFragment.mBluetoothSwitch.setChecked(update);
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public static final class BluetoothTask extends AsyncTask<Void, Void, Void> {
        BluetoothSocket bluetoothSocket = null;
        InputStream inputStream = null;

        public BluetoothTask(BluetoothAdapter bluetoothAdapter, String MacAddress){
            try {
                BluetoothDevice btDevice = bluetoothAdapter.getRemoteDevice(MacAddress);
                bluetoothSocket = btDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                bluetoothSocket.connect();
                inputStream = bluetoothSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                if (inputStream != null) {
                    try {
                        Thread.sleep(1000);
                        int availableBytes = inputStream.available();
                        byte[] buffer = new byte[availableBytes];
                        DataInputStream input = new DataInputStream(inputStream);
                        input.readFully(buffer, 0, buffer.length);
                        MainActivity.Data = new String(buffer);
                        Log.d(TAG, "getData: " + MainActivity.Data);
                        bluetoothSocket.close();
                    } catch (IOException e) { e.printStackTrace(); }
                }
            } catch (Exception e) { e.printStackTrace(); }
            Log.d(TAG, "getData: done");
            return null;
        }
    }
}
