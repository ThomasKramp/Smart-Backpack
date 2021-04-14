package com.example.smartbackpack.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbackpack.List.ItemActivity;
import com.example.smartbackpack.List.ListAdapter;
import com.example.smartbackpack.List.ListItem;
import com.example.smartbackpack.List.ListItemListener;
import com.example.smartbackpack.R;

import java.util.ArrayList;

public class ListFragment extends Fragment implements ListItemListener {
    public static final int TEXT_REQUEST = 1;

    ArrayList<ListItem> items = new ArrayList<>();
    ListAdapter mListAdapter;
    RecyclerView mRecyclerView;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        items.add(new ListItem(null, "Lorem Lorem", 3));
        items.add(new ListItem(null, "Lorem Ipsum", 7));
        items.add(new ListItem(null, "Ipsum Ipsum", 8));

        mRecyclerView = view.findViewById(R.id.list_scroller);
        mListAdapter = new ListAdapter(getContext(), items, this);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.list_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra(ItemActivity.tIntentType, "Add");
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });
        
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == ItemActivity.Result_Ok) {
                String intentType = data.getStringExtra(ItemActivity.tIntentType);
                // Toast.makeText(this, intentType, Toast.LENGTH_SHORT).show();

                int index = data.getIntExtra(ItemActivity.tIndex, -1);
                String name = data.getStringExtra(ItemActivity.tName);
                int amount = data.getIntExtra(ItemActivity.tAmount, 0);
                Bitmap image = (Bitmap) data.getParcelableExtra(ItemActivity.tImage);

                if (intentType.equals("Add")) addItem(index, image, name, amount);
                else if (intentType.equals("Edit")) editItem(index, image, name, amount);
            }
        }
    }

    public void addItem(int index, Bitmap image, String name, int amount) {
        if (index == -1) index = items.size();
        if (name.equals("")) name = "No name given";
        if (amount == 0) amount = 1;
        items.add(index, new ListItem(image, name, amount));
        mListAdapter.notifyItemInserted(index);
    }

    public void editItem(int index, Bitmap image, String name, int amount) {
        ListItem item = items.get(index);
        if (image != null) item.setImage(image);
        if (!name.equals("")) item.setName(name);
        if (amount != 0) item.setAmount(amount);
        items.set(index, item);
        mListAdapter.notifyItemChanged(index);
    }

    @Override
    public void onItemClick(int position) {
        ListItem oldItem = items.get(position);
        Intent intent = new Intent(getContext(), ItemActivity.class);
        intent.putExtra(ItemActivity.tIntentType, "Edit");
        intent.putExtra(ItemActivity.tIndex, position);
        intent.putExtra(ItemActivity.tName, oldItem.getName());
        intent.putExtra(ItemActivity.tAmount, oldItem.getAmount());
        intent.putExtra(ItemActivity.tImage, oldItem.getImage());
        startActivityForResult(intent, TEXT_REQUEST);
    }

    @Override
    public void onDeleteClick(int position) {
        items.remove(position);
        mListAdapter.notifyItemRemoved(position);
    }
}