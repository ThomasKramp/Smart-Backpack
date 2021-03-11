package com.example.smartbackpack.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.smartbackpack.R;

public class ItemActivity extends AppCompatActivity {
    public static final String Name = "Name";
    public static final String Amount = "Amount";
    public static final String IntentType = "IntentType";

    String type = "";
    EditText mNameInput;
    EditText mAmountInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        mNameInput = findViewById(R.id.name_input);
        mAmountInput = findViewById(R.id.amount_input);
        type = getIntent().getStringExtra(IntentType);
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(IntentType, type);
        replyIntent.putExtra(Name, mNameInput.getText().toString());
        replyIntent.putExtra(Amount, Integer.parseInt(mAmountInput.getText().toString()));
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}