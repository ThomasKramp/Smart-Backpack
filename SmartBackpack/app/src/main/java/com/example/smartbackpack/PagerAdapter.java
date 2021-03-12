package com.example.smartbackpack;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smartbackpack.HomeFragment;
import com.example.smartbackpack.ListFragment;
import com.example.smartbackpack.MoistureFragment;
import com.example.smartbackpack.WeightFragment;

public class PagerAdapter extends FragmentStatePagerAdapter
{
    int mNumberOfTabs;

    public PagerAdapter(@NonNull FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumberOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        switch (position)
        {
            case 0: return new HomeFragment();
            case 1: return new MoistureFragment();
            case 2: return new WeightFragment();
            case 3: return new ListFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
