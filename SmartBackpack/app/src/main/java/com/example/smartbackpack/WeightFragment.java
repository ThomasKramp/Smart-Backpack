package com.example.smartbackpack;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WeightFragment extends Fragment {
    private static final String TAG = "WeightFragment";
    private static final String DATA_TAG = "Value: ";

    EditText mUserWeightInput;
    TextView mMeasureData;
    TextView mWeightWarning;
    TextView mShowValues;

    double userWeight = 100;     // Weight in kilogram
    double backPackWeight = 0;   // Weight in kilogram
    String WeightMessage = "";

    public WeightFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weight, container, false);

        mUserWeightInput = view.findViewById(R.id.user_weight_input);
        mMeasureData = view.findViewById(R.id.measured_data);
        mWeightWarning = view.findViewById(R.id.weight_warning);
        mShowValues = view.findViewById(R.id.view_data);

        view.findViewById(R.id.measure_weight_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckUserWeightValidity();
                CalculateBackPackWeight(MainActivity.WeightData);
                CheckBackPackWeight();
                mMeasureData.setText("Backpack Weight: " + backPackWeight + " kg");
                mWeightWarning.setText(WeightMessage);
                mShowValues.setText(MainActivity.WeightData);
            }
        });
        return view;
    }

    private void CheckUserWeightValidity() {
        String input = mUserWeightInput.getText().toString();
        if (input.isEmpty()) return;
        double userInput = Double.parseDouble(input);
        if (userInput <= 40 || userInput >= 150)
            Toast.makeText(getActivity(), "Give Your Real Weight", Toast.LENGTH_SHORT).show();
        else userWeight = userInput;
        Log.d(TAG, "CheckUserWeightValidity: User Weight is" + userWeight);
    }

    private void CalculateBackPackWeight(String weightData) {
        if (!weightData.isEmpty()){
            if (weightData.length() >= 4 + DATA_TAG.length()){
                String[] separated = weightData.split(DATA_TAG);
                if (separated.length > 1) {
                    backPackWeight = 0;
                    for (String weightString: separated){
                        Log.d(TAG, "CheckBackPackWeight: Values: " + weightString);
                        if (weightString.isEmpty()) continue;
                        backPackWeight += Double.parseDouble(weightString);
                    }
                    backPackWeight /= separated.length - 1.0; // First Value is always " ";
                }
                Log.d(TAG, "CheckBackPackWeight: Backpack Weight: " + backPackWeight);
            }
        } else Log.d(TAG, "CheckBackPackWeight: No Data Received");
    }

    private void CheckBackPackWeight() {
        // Test values are being used
        // Therefore the maximum is value (4096) will be reverted to 25 kilograms
        backPackWeight = backPackWeight * 25.0 / 4096.0;
        backPackWeight = Math.round(backPackWeight * 100) / 100.0;  // 2 Decimals after comma
        if (backPackWeight >= userWeight * 0.20)
            WeightMessage = "You're carrying too much!!!";
        else if (backPackWeight >= userWeight * 0.10)
            WeightMessage = "It's going to be rough, but doable";
        else
            WeightMessage = "You're fine";
    }
}