package com.example.smartbackpack.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartbackpack.MainActivity;
import com.example.smartbackpack.R;

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
                    CheckBackPackMoisture(v, MainActivity.MoistureData);
                }
            });
            Buttons.add(tempButton);
        }
    }

    private void CheckBackPackMoisture(View view, List<String> moistureData) {
        int index = Buttons.indexOf(view);
        if (!moistureData.isEmpty()){
            String data = moistureData.get(index);
            Log.d(TAG, "CheckBackPackMoisture: " + data);
            if (data.length() > DATA_TAG.length()){
                data = data.substring(DATA_TAG.length());
                int moistureLevel = Integer.parseInt(data);
                if (moistureLevel != 0) view.setBackgroundResource(R.color.colorPrimaryDark);
                else view.setBackgroundResource(R.color.colorPrimary);
            }
        } else Log.d(TAG, "CheckBackPackWeight: No Data Received");
    }

}