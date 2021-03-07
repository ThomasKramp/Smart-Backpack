package com.example.smartbackpack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class WeightActivity extends AppCompatActivity
{
    private TextView ShowMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        ShowMeasurement = (TextView) findViewById(R.id.MeasuredData);
    }

    public void measureData(View view)
    {
        ShowMeasurement.setText(randomWeight(view));
    }

    public String randomWeight(View view)
    {
        int min = 2;
        int max = 35;
        int RandomWeight = (int)(Math.random() * (max - min + 1) + min);

        return String.valueOf(RandomWeight) + "kg";
    }
}