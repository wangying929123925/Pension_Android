package com.warehouse.news.homefragment.repair;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.warehouse.news.R;
import com.warehouse.news.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HeadlineMessageListFragment extends Fragment {
    public HeadlineMessageListFragmentAdapter adapter;
    FragmentHomeBinding viewDataBinding;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> messageFragments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        adapter = new HeadlineMessageListFragmentAdapter(getChildFragmentManager());
        viewDataBinding.tablayout.setTabMode(TabLayout.MODE_FIXED);
        viewDataBinding.viewpager.setAdapter(adapter);
        viewDataBinding.tablayout.setupWithViewPager(viewDataBinding.viewpager);
        viewDataBinding.viewpager.setOffscreenPageLimit(1);
        titles.add("全部消息");
        titles.add("维修消息");
        titles.add("巡检消息");
        titles.add("支付消息");
        messageFragments.add(MessageListFragment.newInstance("ALL"));
        messageFragments.add(MessageListFragment.newInstance("MDMC_TOPIC"));
        messageFragments.add(MessageListFragment.newInstance("IMC_TOPIC"));
        messageFragments.add(MessageListFragment.newInstance("PAY_TOPIC"));
        adapter.setTitle(titles,messageFragments);
        return viewDataBinding.getRoot();
    }
}
