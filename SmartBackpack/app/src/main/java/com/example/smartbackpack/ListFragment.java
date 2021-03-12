package com.example.smartbackpack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.smartbackpack.List.ItemActivity;
import com.example.smartbackpack.List.ListAdapter;
import com.example.smartbackpack.List.ListItem;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    private static final String TAG = "ListActivity";
    public static final int TEXT_REQUEST = 1;

    ArrayList<ListItem> items = new ArrayList<>();
    ListAdapter mAdapter;
    RecyclerView mRecyclerView;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Lorem Lorem", 3));
        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Lorem Ipsum", 7));
        items.add(new ListItem(R.drawable.ic_launcher_foreground, "Ipsum Ipsum", 8));

        mRecyclerView = view.findViewById(R.id.list_scroller);
        mAdapter = new ListAdapter(getContext(), items);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        view.findViewById(R.id.list_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra(ItemActivity.IntentType, "Add");
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        view.findViewById(R.id.list_edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra(ItemActivity.IntentType, "Edit");
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        view.findViewById(R.id.list_remove_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ItemActivity.class);
                intent.putExtra(ItemActivity.IntentType, "Remove");
                startActivityForResult(intent, TEXT_REQUEST);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == ItemActivity.Result_Ok)
            {
                String intentType = data.getStringExtra(ItemActivity.IntentType);
                // Toast.makeText(this, intentType, Toast.LENGTH_SHORT).show();

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
        else Toast.makeText(getContext(), name + " not in list", Toast.LENGTH_LONG).show();
    }
}