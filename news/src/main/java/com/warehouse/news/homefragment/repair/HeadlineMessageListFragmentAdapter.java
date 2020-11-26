package com.warehouse.news.homefragment.repair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class HeadlineMessageListFragmentAdapter extends FragmentPagerAdapter {
    private List<String> mTitle;
    private List<Fragment> fragments;
    private int mCount;
    public HeadlineMessageListFragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    public void setTitle(List<String> titles,List<Fragment> fragments) {
        this.mTitle = titles;
        this.fragments = fragments;
        mCount = titles.size();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return mCount;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle.get(position);
    }
}
