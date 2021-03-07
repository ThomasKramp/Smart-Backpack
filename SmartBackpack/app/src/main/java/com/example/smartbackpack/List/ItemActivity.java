package com.example.smartbackpack.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.smartbackpack.R;

public class ItemActivity extends AppCompatActivity {
    public static final String REPLY = "Reply";

    EditText mNameInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        mNameInput = findViewById(R.id.name_input);
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent();
        String name = mNameInput.getText().toString();
        replyIntent.putExtra(REPLY, name);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}