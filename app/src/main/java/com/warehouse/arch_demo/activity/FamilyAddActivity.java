package com.warehouse.arch_demo.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.warehouse.arch_demo.R;
import com.warehouse.base.activity.BaseActivity;

import butterknife.ButterKnife;

public class FamilyAddActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_add);
        ButterKnife.bind(this);

    }
}
