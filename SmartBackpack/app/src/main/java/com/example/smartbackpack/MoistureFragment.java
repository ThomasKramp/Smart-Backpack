package com.example.smartbackpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoistureFragment extends Fragment {
    private static final String TAG = "MoistureFragment";
    private static final String DATA_TAG = "Moisture: ";

    double backPackWeight = 0;   // Weight in kilogram
    String WeightMessage = "";

    public MoistureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moisture, container, false);
    }

    private void GetBackPackMoisture(String weightData) {
        if (!weightData.isEmpty()){
            if (weightData.length() >= 4 + DATA_TAG.length()){
                String[] separated = weightData.split(DATA_TAG);
                int teller = 0;
                if (separated.length > 1) {
                    backPackWeight = 0;
                    teller++;
                    for (String moistureString: separated){
                        Log.d(TAG, "CheckBackPackWeight: Sensor" + teller + ": " + moistureString);
                        if (moistureString.isEmpty()) continue;
                    }
                }
            }
        } else Log.d(TAG, "CheckBackPackWeight: No Data Received");
    }

    private void CheckBackPackMoisture() {

    }
}