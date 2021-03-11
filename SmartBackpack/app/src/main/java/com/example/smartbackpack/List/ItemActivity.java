package com.example.smartbackpack.List;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        HideInputs();
    }

    public void HideInputs(){
        if (type.equals("Remove")){
            findViewById(R.id.amount_text).setVisibility(View.INVISIBLE);
            mAmountInput.setVisibility(View.INVISIBLE);
        }
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(IntentType, type);

        int index = 0;
        try {
            index = Integer.parseInt(mIndexInput.getText().toString());
            replyIntent.putExtra(Index, index);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No index given: ", e);
        }

        String name = "";
        try {
            name = mNameInput.getText().toString();
            replyIntent.putExtra(Name, name);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No name given: ", e);
        }

        int amount = 0;
        try {
            amount = Integer.parseInt(mAmountInput.getText().toString());
            replyIntent.putExtra(Amount, amount);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No amount given: ", e);
        }

        setResult(RESULT_OK, replyIntent);
        if (CheckIfFilled(index, name)) finish();
        else Toast.makeText(this, "Not everything is filled", Toast.LENGTH_LONG).show();
    }

    public Boolean CheckIfFilled(int index, String name){
        if (type.equals("Add")) {
            if (name.equals("")) return false;
        } else if (type.equals("Edit")) {
            if (index == 0) return false;
        } else if (type.equals("Remove")) {
            if (index == 0 && name.equals("")) return false;
        }
        return true;
    }
}