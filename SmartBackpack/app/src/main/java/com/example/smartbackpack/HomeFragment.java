package com.example.smartbackpack;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbackpack.Bluetooth.BluetoothFragment;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Handle the Child Fragment
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        BluetoothFragment bluetoothFragment = new BluetoothFragment();
        ft.replace(R.id.BluetoothFragmentContainer, bluetoothFragment, "BluetoothFragment");
        ft.addToBackStack(null);
        ft.commit();

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}