package com.warehouse.arch_demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.warehouse.arch_demo.R;
import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.widget.MyToolBar;

import butterknife.ButterKnife;

public class FamilyDetailActivity extends BaseActivity {
    private TextView family_name;
    private TextView family_sex;
    private TextView family_relationship;
    private TextView family_address;
    private Button unbind_family_member;
    MyToolBar mToolBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_detail);
        initView();
    }

    private void initView() {
        family_name = findViewById(R.id.family_name);
        family_sex = findViewById(R.id.family_sex);
        family_relationship = findViewById(R.id.family_relationship);
        family_address = findViewById(R.id.family_address);
        unbind_family_member = findViewById(R.id.unbind_family_member);
        mToolBar = findViewById(R.id.family_detail_toolbar);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void unbindFamily() {

    }
}
