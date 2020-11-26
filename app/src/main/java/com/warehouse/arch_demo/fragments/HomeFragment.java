package com.warehouse.arch_demo.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.application.ArchDemoApplication;
import com.warehouse.base.widget.MyToolBar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    MyToolBar myToolBar;
    private Banner mBanner;
    private List<String> images = new ArrayList<>();
    private View mView;
    View viewHeader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       super.onCreateView(inflater, container, savedInstanceState);
        mView=inflater.inflate(R.layout.fragment_home,null);
        viewHeader = mView.findViewById(R.id.home_banner);
        mBanner = viewHeader.findViewById(R.id.banner);
        initView();
        setBannerData();
        return mView;
    }

    private void initView() {
        //设置banner样式
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
    }

    /**
     * 轮播图数据
     */
    private void setBannerData() {
        //设置图片集合
        images.add("./img/banner_1.jpg");
        images.add("./img/banner_2.png");
        images.add("./img/banner_3.jpg");
        images.add("./img/banner_4.jpg");
        images.add("./img/banner_5.jpg");
        mBanner.setImages(images);
        //设置标题集合（当banner样式有显示title时）
        //mBanner.setBannerTitles(titles);

        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        mBanner.start();
    }
    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(getContext().getApplicationContext()).load(path).into(imageView);
        }
    }
}
