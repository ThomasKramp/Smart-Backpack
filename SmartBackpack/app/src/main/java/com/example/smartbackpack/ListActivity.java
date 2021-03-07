package com.example.smartbackpack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.smartbackpack.List.ItemActivity;
import com.example.smartbackpack.List.ListAdapter;
import com.example.smartbackpack.List.ListItem;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    private static final String IntentType = "IntentType";
    public static final int TEXT_REQUEST = 1;

    ArrayList<ListItem> items = new ArrayList<>();
    ListAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Lorem Lorem", 3));
        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Lorem Ipsum", 7));
        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Ipsum Ipsum", 8));

        mRecyclerView = findViewById(R.id.list_scroller);
        mAdapter = new ListAdapter(this, items);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void AddItem(View view) {
        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Pants", 4));
        mAdapter.notifyItemInserted(items.size());
    }

    public void RemoveItem(View view) {
        Intent intent = new Intent(this, ItemActivity.class);
        intent.putExtra(IntentType, "Remove Item");
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK){
                String reply = data.getStringExtra(ItemActivity.REPLY);
                Toast.makeText(this, reply, Toast.LENGTH_SHORT).show();
            }
        }
    }
}