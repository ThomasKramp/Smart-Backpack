package com.example.smartbackpack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.smartbackpack.List.ListAdapter;

public class ListActivity extends AppCompatActivity {

    String[] vItemNames = {
            "Lorem Lorem",
            "Lorem Ipsum",
            "Ipsum Ipsum"
    };

    int[] vItemAmounts = { 3, 7, 8};

    ListAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRecyclerView = findViewById(R.id.list_scroller);
        mAdapter = new ListAdapter(this, vItemNames, vItemAmounts);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}