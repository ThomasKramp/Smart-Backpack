package com.example.smartbackpack.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.smartbackpack.R;

public class ItemActivity extends AppCompatActivity {
    private static final String TAG = "ItemActivity";
    public static final String Index = "Index";
    public static final String Name = "Name";
    public static final String Amount = "Amount";
    public static final String IntentType = "IntentType";

    String type = "";
    EditText mIndexInput;
    EditText mNameInput;
    EditText mAmountInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        mIndexInput = findViewById(R.id.position_input);
        mNameInput = findViewById(R.id.name_input);
        mAmountInput = findViewById(R.id.amount_input);
        type = getIntent().getStringExtra(IntentType);
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(IntentType, type);

        try {
            int index = Integer.parseInt(mIndexInput.getText().toString());
            replyIntent.putExtra(Index, index);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No index given: ", e);
        }
        try {
            String name = mNameInput.getText().toString();
            replyIntent.putExtra(Name, name);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No name given: ", e);
        }
        try {
            int amount = Integer.parseInt(mAmountInput.getText().toString());
            replyIntent.putExtra(Amount, amount);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No amount given: ", e);
        }

        setResult(RESULT_OK, replyIntent);
        finish();
    }
}