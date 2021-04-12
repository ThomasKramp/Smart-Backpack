package com.example.smartbackpack;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbackpack.Bluetooth.BluetoothFragment;
import com.example.smartbackpack.Bluetooth.BluetoothReceiver;
import com.example.smartbackpack.Bluetooth.BluetoothReceiverListener;
import com.google.android.material.tabs.TabLayout;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements BluetoothReceiverListener {
    private static final String TAG = "MainActivity";
    private BluetoothReceiver btReceiver;
    private PagerAdapter adapter;
    private static BluetoothTask bluetoothTask;
    public static List<String> WeightData;
    public static List<String> MoistureData;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    public static NotificationManager mNotifyManager;
    public static final int NOTIFICATION_ID = 0;


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

        createNotificationChannel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(btReceiver);
        if (bluetoothTask != null) bluetoothTask.cancel(true);
    }

    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
    public static NotificationCompat.Builder getNotificationBuilder(android.content.Context context, String id){
        NotificationCompat.Builder notifyBuilder;
        if (id == "Bluetooth"){
            notifyBuilder =  new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                    .setContentTitle("Connection Lost!")
                    .setContentText("The connection to Bluetooth has been lost!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        else if (id == "Weight"){
            notifyBuilder =  new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                    .setContentTitle("Weight Exceeded!")
                    .setContentText("The weight limit of your backpack has been exceeded")
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        else if (id == "List"){
            notifyBuilder =  new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                    .setContentTitle("Item Lost")
                    .setContentText("You may have lost an item!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        else if (id == "Moisture"){
            notifyBuilder =  new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                    .setContentTitle("Wetness!!")
                    .setContentText("There has been a leak!!")
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        else{
            notifyBuilder =  new NotificationCompat.Builder(context,PRIMARY_CHANNEL_ID)
                    .setContentTitle("You've been notified!")
                    .setContentText("This is your notification text.")
                    .setSmallIcon(R.drawable.ic_launcher_foreground);
        }
        return notifyBuilder;
    }


    public static String ListToString(List<String> list){
        StringBuilder str = new StringBuilder();
        for (String string: list){
            Log.d(TAG, "CheckBackPackWeight: Values: " + string);
            if (string.isEmpty()) continue;
            str.append(string);
        }
        return str.toString();
    }

    @Override
    public void updateBluetoothSwitch(boolean update) {
        // Update switch
        BluetoothFragment.mBluetoothSwitch.setChecked(update);
    }

    public static void StartBluetoothTask(BluetoothAdapter bluetoothAdapter, String MacAddress){
        bluetoothTask = new MainActivity.BluetoothTask(bluetoothAdapter, MacAddress);
        bluetoothTask.execute();
    }

    public static final class BluetoothTask extends AsyncTask<Void, Void, Void> {
        private static final String DATA_TAG = "\n";
        BluetoothSocket bluetoothSocket = null;
        InputStream inputStream = null;

        private Runnable updater;
        private boolean stop = false;
        private int delay = 1000; // 1 seconden
        final Handler timerHandler = new Handler();

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
            updater = new Runnable() {
                @Override
                public void run() {
                    try {
                        if (inputStream != null) {
                            try {
                                Thread.sleep(1000);
                                //bluetoothSocket.connect();
                                int availableBytes = inputStream.available();
                                byte[] buffer = new byte[availableBytes];
                                DataInputStream input = new DataInputStream(inputStream);
                                input.readFully(buffer, 0, buffer.length);
                                String data = new String(buffer);
                                String[] separated = data.split(DATA_TAG);
                                MainActivity.WeightData = new ArrayList<>();
                                MainActivity.MoistureData = new ArrayList<>();
                                for (String dataString: separated){
                                    Log.d(TAG, "CheckBackPackWeight: Data: " + dataString);
                                    if (dataString.isEmpty()) continue;
                                    else if (dataString.contains(WeightFragment.DATA_TAG))
                                        WeightData.add(dataString);
                                    else if (dataString.contains(MoistureFragment.DATA_TAG))
                                        MoistureData.add(dataString);
                                }
                                Log.d(TAG, "getData: " + data);
                                //bluetoothSocket.close();
                            } catch (IOException e) { e.printStackTrace(); }
                        }
                    } catch (Exception e) { e.printStackTrace(); }
                    Log.d(TAG, "getData: done");
                    // Zet de timer
                    if(!stop) timerHandler.postDelayed(updater, delay);
                }
            };
            timerHandler.post(updater);
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            try { bluetoothSocket.close();
            } catch (IOException e) { e.printStackTrace(); }
            stop = true;
            timerHandler.removeCallbacksAndMessages(null);
        }
    }
}
