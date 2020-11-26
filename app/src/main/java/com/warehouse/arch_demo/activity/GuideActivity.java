package com.warehouse.arch_demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.warehouse.arch_demo.R;
import com.warehouse.base.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends BaseActivity {
private ViewPager mViewPager;
private Button mBtnStart;

    //获取图片资源
    int[] imgRes = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3, R
            .drawable.guide_4};
    private List<View> mViewList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        mViewPager = findViewById(R.id.viewpager);
        mBtnStart = findViewById(R.id.btn_start);
        initData();
        MyPagerAdapter adapter = new MyPagerAdapter();
        mViewPager.setAdapter(adapter);
        mViewPager.setOnPageChangeListener(new PagePositionLister());
        mBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GuideActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {

        for (int i = 0; i < imgRes.length; i++) {
            View inflate = getLayoutInflater().inflate(R.layout.guide_item, null);
            ImageView ivGuide = (ImageView) inflate.findViewById(R.id.iv_guide);
            ivGuide.setBackgroundResource(imgRes[i]);
            mViewList.add(inflate);
        }
    }
    /**
     * viewPager适配器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mViewList == null ? 0 : mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = mViewList.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));
        }
    }

    private class PagePositionLister implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //如果滑动到最后一张,显示按钮
            if (position == mViewList.size() - 1) {
                mBtnStart.setVisibility(View.VISIBLE);
            } else {
                mBtnStart.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageSelected(int position) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }

}
