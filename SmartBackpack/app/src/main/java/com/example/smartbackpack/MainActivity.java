package com.example.smartbackpack;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.viewpager.widget.ViewPager;

import com.example.smartbackpack.Bluetooth.BluetoothReceiver;
import com.example.smartbackpack.Bluetooth.BluetoothReceiverListener;
import com.example.smartbackpack.Fragments.BluetoothFragment;
import com.example.smartbackpack.Fragments.MoistureFragment;
import com.example.smartbackpack.Fragments.NotificationId;
import com.example.smartbackpack.Fragments.PagerAdapter;
import com.example.smartbackpack.Fragments.WeightFragment;
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
    ViewPager viewPager;
    int currentTabIndex;

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
        viewPager = findViewById(R.id.pager);
        adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        // Setting a listener for clicks.
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                        currentTabIndex = tab.getPosition();
                        Log.d(TAG, String.valueOf(currentTabIndex));
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {
                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {
                    }
                });

        Intent data = getIntent();
        if (data != null) viewPager.setCurrentItem(data.getIntExtra("position", 0));
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
                    "Backpack Problems", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Backpack Problems");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    public static NotificationCompat.Builder getNotificationBuilder(android.content.Context context,
                                                                    NotificationId id){
        Intent notificationIntent = new Intent(context, MainActivity.class);
        NotificationCompat.Builder notifyBuilder =  new NotificationCompat
                .Builder(context,PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
        switch (id) {
            case BLUETOOTH:
                notificationIntent.putExtra("position", 0);
                notifyBuilder.setContentTitle("Connection Lost!")
                        .setContentText("Lost the Bluetooth connection");
                StopBluetoothTask();
                break;
            case MOISTURE:
                notificationIntent.putExtra("position", 1);
                notifyBuilder.setContentTitle("Wetness!!")
                        .setContentText("There has been a leak!!");
                break;
            case WEIGHT:
                // Made in case there is a need for a Weight notification
                notificationIntent.putExtra("position", 2);
                notifyBuilder.setContentTitle("Weight Exceeded!")
                        .setContentText("The weight limit of your backpack has been exceeded");
                break;
            case LIST:
                notificationIntent.putExtra("position", 3);
                notifyBuilder.setContentTitle("Too Many Items")
                        .setContentText("You're carrying too many items");
                break;
            default:
                break;
        }
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifyBuilder.setContentIntent(notificationPendingIntent);
        return notifyBuilder;
    }

    public static void sendNotification(Context context, NotificationId fragmentId){
        NotificationCompat.Builder notifyBuilder = MainActivity.getNotificationBuilder(context, fragmentId);
        MainActivity.mNotifyManager.notify(MainActivity.NOTIFICATION_ID, notifyBuilder.build());
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
        if (currentTabIndex <= 1)
            BluetoothFragment.mBluetoothSwitch.setChecked(update);
    }

    public static void StartBluetoothTask(Context context, BluetoothAdapter bluetoothAdapter, String MacAddress){
        bluetoothTask = new MainActivity.BluetoothTask(context, bluetoothAdapter, MacAddress);
        bluetoothTask.execute();
    }
    public static void StopBluetoothTask(){
        if (bluetoothTask != null)
            bluetoothTask.cancel(true);
    }

    public static final class BluetoothTask extends AsyncTask<Void, Void, Void> {
        final String DATA_TAG = "\n";
        BluetoothSocket bluetoothSocket = null;
        InputStream inputStream = null;
        Context mContext;

        Runnable updater;
        int delay = 10000; // 10 seconden
        final Handler timerHandler = new Handler();

        public BluetoothTask(Context context, BluetoothAdapter bluetoothAdapter, String MacAddress){
            mContext = context;
            try {
                // Connect to remote device via bluetooth
                BluetoothDevice btDevice = bluetoothAdapter.getRemoteDevice(MacAddress);
                bluetoothSocket = btDevice.createInsecureRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
                bluetoothSocket.connect();
                inputStream = bluetoothSocket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mContext, "Couldn't connect", Toast.LENGTH_SHORT).show();
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
                                // Wait for all bits to be send
                                Thread.sleep(1000);
                                // Receive all send bits
                                int availableBytes = inputStream.available();
                                if (availableBytes == 0)
                                    MainActivity.sendNotification(mContext, NotificationId.BLUETOOTH);
                                byte[] buffer = new byte[availableBytes];
                                DataInputStream input = new DataInputStream(inputStream);
                                // Put all received bits into string format
                                input.readFully(buffer, 0, buffer.length);
                                String data = new String(buffer);
                                Log.d(TAG, "getData: " + data);
                                // Separate weight and moisture data
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
                                // Continues moisture check
                                for (Button sensor: MoistureFragment.Sensors)
                                    MoistureFragment.CheckBackPackMoisture(sensor, MainActivity.MoistureData);
                                // Send notification if moisture sensor is triggered
                                if (MoistureFragment.TriggeredSensors){
                                    MainActivity.sendNotification(mContext, NotificationId.MOISTURE);
                                    MoistureFragment.TriggeredSensors = false;
                                }
                            } catch (IOException e) {
                                // Send notification if bluetooth connection fails
                                e.printStackTrace();
                                MainActivity.sendNotification(mContext, NotificationId.BLUETOOTH);
                            }
                        }
                    } catch (Exception e) {
                        // Send notification if bluetooth connection fails
                        e.printStackTrace();
                        MainActivity.sendNotification(mContext, NotificationId.BLUETOOTH);
                    }
                    Log.d(TAG, "getData: done");
                    // Sets the timer
                    if(!isCancelled())
                        timerHandler.postDelayed(updater, delay);
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
            timerHandler.removeCallbacksAndMessages(null);
        }
    }
}
