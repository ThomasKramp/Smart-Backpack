package com.example.smartbackpack.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.smartbackpack.R;

public class ItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "ItemActivity";
    public static final String tIntentType = "IntentType";
    public static final String tIndex = "Index";
    public static final String tName = "Name";
    public static final String tAmount = "Amount";
    public static final String tImage = "Image";
    public static final int Result_Ok = RESULT_OK;

    Spinner mItemSelect;
    EditText mNameInput;
    EditText mAmountInput;
    ImageView mImageHolder;

    String vType;
    int vIndex;
    String vName;
    int vAmount;
    Bitmap vImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        mItemSelect = findViewById(R.id.pre_set_item_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.pre_set_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (mItemSelect != null){
            mItemSelect.setOnItemSelectedListener(this);
            mItemSelect.setAdapter(adapter);
        }

        mNameInput = findViewById(R.id.name_input);
        mAmountInput = findViewById(R.id.amount_input);
        mImageHolder = findViewById(R.id.image_holder);

        getIntentData();
    }

    private void getIntentData() {
        Intent data = getIntent();
        vType = data.getStringExtra(tIntentType);
        if (vType.equals("Edit")){
            vIndex = data.getIntExtra(tIndex, -1);

            vName = data.getStringExtra(tName);
            mNameInput.setText(vName);

            vAmount = data.getIntExtra(tAmount, 0);
            mAmountInput.setText(Integer.toString(vAmount));

            vImage = (Bitmap) data.getParcelableExtra(ItemActivity.tImage);
            if(vImage == null) mImageHolder.setImageResource(R.drawable.ic_no_image);
            else mImageHolder.setImageBitmap(vImage);
        }
    }

    public void returnReply(View view) {
        Intent replyIntent = new Intent();
        replyIntent.putExtra(tIntentType, vType);
        replyIntent.putExtra(tIndex, vIndex);

        try {
            vName = mNameInput.getText().toString();
            replyIntent.putExtra(tName, vName);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No name given: ", e);
            Toast.makeText(this, "Improper character used", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            vAmount = Integer.parseInt(mAmountInput.getText().toString());
            replyIntent.putExtra(tAmount, vAmount);
        } catch (Exception e) {
            Log.e(TAG, "returnReply: No amount given: ", e);
            Toast.makeText(this, "Number is too big", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            replyIntent.putExtra(tImage, vImage);
        } catch (Exception e) { Log.e(TAG, "returnReply: No Image given: ", e); }

        setResult(RESULT_OK, replyIntent);
        if (checkIfFilled()) finish();
        else Toast.makeText(this, "Not everything is filled", Toast.LENGTH_LONG).show();
    }

    public Boolean checkIfFilled(){
        if (vIndex > -1 || !vName.equals("") || vAmount > 0) return true;
        return false;
    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        assert data != null;    // null check
        vImage = Bitmap.createScaledBitmap((Bitmap) data.getExtras().get("data"), 200, 200, false);
        mImageHolder.setImageBitmap(vImage);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        mNameInput.setText(item);
        if (!item.isEmpty() && item != null){
            int itemId = this.getResources().getIdentifier(item.toLowerCase(), "drawable", this.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), itemId);
            vImage = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 0.1f), (int) (bitmap.getHeight() * 0.1f), false);
            mImageHolder.setImageBitmap(vImage);
        } else {
            mImageHolder.setImageResource(R.drawable.ic_no_image);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}