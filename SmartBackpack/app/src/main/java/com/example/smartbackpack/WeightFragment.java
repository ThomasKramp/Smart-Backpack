package com.example.smartbackpack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class WeightFragment extends Fragment {

    TextView mMeasureData;

    public WeightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);
        mMeasureData = view.findViewById(R.id.measured_data);
        view.findViewById(R.id.measure_weight_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMeasureData.setText(MainActivity.Data);
            }
        });
        return view;
    }
}