package com.warehouse.arch_demo.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.warehouse.arch_demo.R;

import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.pic.ImageWatcher;
import com.warehouse.base.pic.ImageWatcherHelper;

public class SecondFaultDetailsActivity extends BaseActivity implements ImageWatcher.OnPictureLongPressListener{

    private RecyclerView recyclerview;

    private ImageWatcher vImageWatcher;

    private LinearLayout ll_fault_second_number;

    private TextView tv_second_fault_number,tv_second_fault_content,tv_second_fault_company;
    private TextView tv_second_fault_address,tv_second_fault_linkname,tv_second_fault_phone;
    private TextView tv_second_fault_contract,tv_second_fault_time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_details);
    }

    protected void initView() {

    //    setTabTitleText("报障详情");

        Intent intent = getIntent();

        recyclerview = findViewById(R.id.demand_second_detail_recyclerview);

        recyclerview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


        /**
         * 1.1.0
         */
        vImageWatcher = ImageWatcherHelper.with(SecondFaultDetailsActivity.this) // 一般来讲，ImageWatcher尺寸占据全屏
//                .setLoader(new GlideSimpleLoader()) // demo中有简单实现
//                .setIndexProvider(new DotIndexProvider()) // 自定义
                .create();

        vImageWatcher.setOnPictureLongPressListener(this);

        ll_fault_second_number = findViewById(R.id.ll_fault_second_number);

        tv_second_fault_number = findViewById(R.id.tv_second_fault_number);
        tv_second_fault_content = findViewById(R.id.tv_second_fault_content);
        tv_second_fault_company = findViewById(R.id.tv_second_fault_company);   // 故障单位名称
        tv_second_fault_address = findViewById(R.id.tv_second_fault_address);
        tv_second_fault_linkname = findViewById(R.id.tv_second_fault_linkname);
        tv_second_fault_phone = findViewById(R.id.tv_second_fault_phone);
        tv_second_fault_contract = findViewById(R.id.tv_second_fault_contract);  // 合同名称
        tv_second_fault_time = findViewById(R.id.tv_second_fault_time);



    }


    @Override
    public void onPictureLongPress(ImageView v, Uri uri, int pos) {

//        showPopupWindow(pos,uri);

    }

    private View mPopupView;
    private PopupWindow mPopupWindow;
    private Bitmap bitmap;



    }

//   class GlideSimpleLoader implements ImageWatcher.Loader {
//        @Override
//        public void load(Context context, Uri uri, final ImageWatcher.LoadCallback lc) {
//            Glide.with(context).load(uri)
//                    .into(new SimpleTarget<GlideDrawable>() {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                            lc.onResourceReady(resource);
//                        }
//                    });
//        }
//    }
//
//}
