package com.warehouse.arch_demo.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.bean.DeviceDto;
import com.warehouse.base.recyclerview.CommonAdapter;
import com.warehouse.base.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class DeviceFragment extends Fragment {
    private View mView;
    private CommonAdapter<String> mCategoryAdapter;
    private CommonAdapter<DeviceDto> mDeviceAdapter;
    private List<String> mCategoryList = new ArrayList<>();
    private List<DeviceDto> mDeviceList = new ArrayList<>();
    private RecyclerView mCategoryRV,mDeviceRV;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_device, null);
        mCategoryRV = mView.findViewById(R.id.recyclerview_category);
        mDeviceRV = mView.findViewById(R.id.recyclerview_wares);
        mCategoryList.add("煤气");
        mCategoryList.add("门锁");
        mCategoryList.add("摄像头");
        mCategoryList.add("电视");
        //左边列表
        mCategoryAdapter = new CommonAdapter<String>(getContext(), R.layout.template_single_text, mCategoryList) {
            @Override
            protected void convert(ViewHolder viewHolder, String s, int position) {
                viewHolder.setText(R.id.tv_category_name, s);
            }
        };
        mCategoryRV.setAdapter(mCategoryAdapter);
        mCategoryRV.setLayoutManager(new LinearLayoutManager(getContext()));
        mCategoryRV.setItemAnimator(new DefaultItemAnimator());
        mCategoryRV.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
       // mCategoryRV.setOnClickListener(new );
        //右边列表
        mDeviceAdapter = new CommonAdapter<DeviceDto>(getContext(),R.layout.template_device_list,mDeviceList) {
            @Override
            protected void convert(ViewHolder holder, DeviceDto deviceDto, int position) {
                holder.setText(R.id.device_name,deviceDto.getName());
                holder.setImageResource(R.id.img_device_list, com.warehouse.news.R.drawable.ic_message);

            }
        };
        mDeviceRV.setAdapter(mDeviceAdapter);
        mDeviceRV.setLayoutManager(new GridLayoutManager(getContext(),2));
        mDeviceRV.setItemAnimator(new DefaultItemAnimator());
        mDeviceRV.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        return mView;
    }

    private void requestDeviceList() {

    }
}
