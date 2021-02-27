package com.example.smartbackpack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startMoistureActivity(View view) {
        Log.d(TAG, "Go to the moisture activity");
        /*
        Intent messageIntent = new Intent(this, MoistureActivity.class);
        startActivity(messageIntent);
         */
    }

    public void startWeightActivity(View view) {
        Log.d(TAG, "Go to the weight activity");
        /*
        Intent messageIntent = new Intent(this, WeightActivity.class);
        startActivity(messageIntent);
         */
    }

    public void startListActivity(View view) {
        Log.d(TAG, "Go to the list activity");
        /*
        Intent messageIntent = new Intent(this, ListActivity.class);
        startActivity(messageIntent);
         */
    }
}