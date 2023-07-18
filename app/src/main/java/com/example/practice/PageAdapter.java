package com.example.practice;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PageAdapter extends FragmentStateAdapter {

    private static final int NUM_PAGE = 2;

    public PageAdapter(FragmentManager fragmentManager, Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                AllChannelFragment all = new AllChannelFragment();
                return all;
            case 1:
                FavChannelFragment favorite = new FavChannelFragment();
                return favorite;
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return NUM_PAGE;
    }
}
