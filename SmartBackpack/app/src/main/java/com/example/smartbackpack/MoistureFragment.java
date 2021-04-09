package com.example.smartbackpack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MoistureFragment extends Fragment {
    private static final String TAG = "MoistureFragment";
    public static final String DATA_TAG = "Moisture: ";

    List<Button> Buttons = new ArrayList<>();

    public MoistureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moisture, container, false);
        InitializeButtons(view, 8, "moistureSensor");

        return view;
    }

    public void InitializeButtons(View view, int sensorAmount, String sensorPosition){
        for (int sensor = 1; sensor <= sensorAmount; sensor++){
            String button = sensorPosition + sensor;
            int buttonId = getActivity().getResources().getIdentifier(button, "id", getActivity().getPackageName());
            Button tempButton = view.findViewById(buttonId);
            tempButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: hello");
                }
            });
            Buttons.add(tempButton);
        }
    }

    private void CheckBackPackMoisture(String moistureData) {
        if (!moistureData.isEmpty()){
            if (moistureData.length() >= 4 + DATA_TAG.length()){
                String[] separated = moistureData.split(DATA_TAG);
                int teller = 0;
                if (separated.length > 1) {
                    teller++;
                    for (String moistureString: separated){
                        Log.d(TAG, "CheckBackPackWeight: Sensor" + teller + ": " + moistureString);
                        if (moistureString.isEmpty()) continue;
                    }
                }
            }
        } else Log.d(TAG, "CheckBackPackWeight: No Data Received");
    }

}