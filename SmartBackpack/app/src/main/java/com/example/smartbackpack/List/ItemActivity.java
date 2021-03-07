package com.example.smartbackpack.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.smartbackpack.ListActivity;
import com.example.smartbackpack.R;

public class ItemActivity extends AppCompatActivity {
    public static final String Name = "Name";
    public static final String IntentType = "IntentType";

    String intentType = "";
    EditText mNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        mNameInput = findViewById(R.id.name_input);
        intentType = getIntent().getStringExtra(IntentType);
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(IntentType, intentType);
        String name = mNameInput.getText().toString();
        replyIntent.putExtra(Name, name);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}