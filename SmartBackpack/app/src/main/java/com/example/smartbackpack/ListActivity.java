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

// RecyclerReview Help:
// https://www.youtube.com/watch?v=17NbUcEts9c&list=PLrnPJCHvNZuBtTYUuc5Pyo4V7xZ2HNtf4&index=4

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
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

    public void StartItem(View view) {
        Intent intent = new Intent(this, ItemActivity.class);
        if (view.getId() == R.id.list_add_button)
            intent.putExtra(ItemActivity.IntentType, "Add");
        else if (view.getId() == R.id.list_edit_button)
            intent.putExtra(ItemActivity.IntentType, "Edit");
        else if (view.getId() == R.id.list_remove_button)
            intent.putExtra(ItemActivity.IntentType, "Remove");
        else Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        startActivityForResult(intent, TEXT_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK){
                String intentType = data.getStringExtra(ItemActivity.IntentType);
                Toast.makeText(this, intentType, Toast.LENGTH_SHORT).show();

                int index = data.getIntExtra(ItemActivity.Index, -1);
                String name = data.getStringExtra(ItemActivity.Name);
                int amount = data.getIntExtra(ItemActivity.Amount, 0);

                if (intentType.equals("Add")) AddItem(index, name, amount);
                else if (intentType.equals("Edit")) EditItem(index, name, amount);
                else if (intentType.equals("Remove")) RemoveItem(index, name);
            }
        }
    }

    public void AddItem(int index, String name, int amount) {
        if (index == -1) index = items.size();
        if (name.equals("")) name = "No name given";
        if (amount == 0) amount = 1;
        items.add(index, new ListItem(R.drawable.ic_launcher_foreground, name, amount));
        mAdapter.notifyItemInserted(index);
    }

    public void EditItem(int index, String name, int amount) {
        ListItem item = items.get(index);
        if (!name.equals("")) item.setName(name);
        if (!(amount == 0)) item.setAmount(amount);
        items.set(index, item);
        mAdapter.notifyItemChanged(index);
    }

    public void RemoveItem(int index, String name) {
        if (!name.equals("") && index == -1) {
            for (ListItem item: items) {
                if (item.getName().equals(name)) index = items.indexOf(item);
            }
        }
        if (index >= 0) {
            items.remove(index);
            mAdapter.notifyItemRemoved(index);
        }
        else Toast.makeText(this, name + " not in list", Toast.LENGTH_LONG).show();
    }
}