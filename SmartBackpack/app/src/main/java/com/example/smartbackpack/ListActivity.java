package com.example.smartbackpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartbackpack.List.ListAdapter;
import com.example.smartbackpack.List.ListItem;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {

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
        mAdapter = new ListAdapter(this, items.toArray(new ListItem[items.size()]));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void AddItem(View view) {
    }

    public void RemoveItem(View view) {
    }
}