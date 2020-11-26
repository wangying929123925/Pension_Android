package com.warehouse.base.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;


import com.scwang.smartrefresh.layout.util.DensityUtil;
import com.warehouse.base.R;
import com.warehouse.base.pic.BigImagePagerAdapter;
import com.warehouse.base.utils.DialogUtils;

import java.util.ArrayList;

/**
 * Created by liuhangf on 2017/9/21.
 */

public class BigImagePagerActivity extends BaseActivity implements FinishactivityInterface, View.OnClickListener {


    private ViewPager vp;
    private LinearLayout pointLl;
    private ImageView img_back;
    private TextView photo_num;
    private ImageView img_delete;
    private ArrayList<String> imgList = new ArrayList<>();
    private RelativeLayout rl_title;
    BigImagePagerAdapter adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigimagepager);
        initDatas();

    }

    protected void initDatas() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        imgList  = bundle.getStringArrayList("Biglist");
        int position = bundle.getInt("position");
        int showTitle = bundle.getInt("showTitle");
        vp = findViewById(R.id.activity_big_imgpager_vp);
        pointLl = findViewById(R.id.activity_big_imgpager_discover_point);
        rl_title = findViewById(R.id.image_pager_activity_title);
        if (showTitle == 1) {//不显示删除图片
            rl_title.setVisibility(View.GONE);
        }
        adapter = new BigImagePagerAdapter(this);
        adapter.setDatas(imgList);
        vp.setAdapter(adapter);
        vp.setCurrentItem(position);
        addPoint(imgList.size());
        changePoint(imgList.size());
        img_back = findViewById(R.id.photo_backTo);
        photo_num = findViewById(R.id.photo_allTitleName);
        img_delete = findViewById(R.id.photo_delete);
        img_back.setOnClickListener(this);
        img_delete.setOnClickListener(this);
        photo_num.setText((position+1)+"/"+imgList.size());
    }

    private void changePoint(final int size) {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                for (int i = 0; i < size; i++) {
                    ImageView pointIv = (ImageView) pointLl.getChildAt(i);
                    pointIv.setImageResource(R.drawable.lunpo);
                }
                ImageView imageView = (ImageView) pointLl.getChildAt(position);
                imageView.setImageResource(R.drawable.lunpo_dangqian);
                photo_num.setText((position+1)+"/"+size);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addPoint(int size) {
        for (int i = 0; i <size ; i++) {
            ImageView pointImg = new ImageView(this);
//            pointImg.setPadding(0,0, DensityUtil.dip2px( this, 9),0);
            pointImg.setPadding(0,0, 5,0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pointImg.setLayoutParams(params);
            pointLl.addView(pointImg);
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.photo_backTo) {
            finish();
        } else if (i == R.id.photo_delete) {
            DialogUtils.showDialog(this, "确认删除图片吗？", "", "取消", "确认", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    int position = vp.getCurrentItem();
                    imgList.remove(position);
                    photo_num.setText((position + 1) + "/" + imgList.size());
                    addPoint(imgList.size());
                    changePoint(imgList.size());
                    if (imgList.size() == 0) {
                        finish();
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }

    }

    @Override
    public void finish() {
        Intent it = new Intent();
        it.putStringArrayListExtra("photos",imgList);
        setResult(301,it);
        super.finish();
    }

    @Override
    public void setFinishactivityInterface() {
        finish();
    }
}
