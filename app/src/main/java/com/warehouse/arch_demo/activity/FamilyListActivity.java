package com.warehouse.arch_demo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.bean.FamilyDto;
import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.recyclerview.CommonAdapter;
import com.warehouse.base.recyclerview.MultiItemTypeAdapter;
import com.warehouse.base.recyclerview.base.ViewHolder;
import com.warehouse.base.widget.MyToolBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FamilyListActivity extends BaseActivity {
    private CommonAdapter<FamilyDto> familyDtoCommonAdapter;
    List<FamilyDto> familyDtos = new ArrayList<>();
   // @BindView(R.id.family_list_toolbar)
    MyToolBar mToolBar;
  //  @BindView(R.id.img_family_add)
    ImageView img_add;
  //  @BindView(R.id.rv_family_list)
    RecyclerView mRecyclerView;
    private Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_list);
        mContext = this;
       // ButterKnife.bind(this);
        mToolBar = findViewById(R.id.family_list_toolbar);
        img_add = findViewById(R.id.img_family_add);
        mRecyclerView = findViewById(R.id.rv_family_list);
        initToolbar();
        initData();
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FamilyAddActivity.class);
                startActivity(intent);
            }
        });
     familyDtoCommonAdapter = new CommonAdapter<FamilyDto>(mContext,R.layout.template_family_list,familyDtos) {
       @Override
       protected void convert(ViewHolder holder, FamilyDto familyDto, int position) {

       }
    };
    mRecyclerView.setAdapter(familyDtoCommonAdapter);
    familyDtoCommonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
            //跳转详情页面
            Intent intent = new Intent(mContext, FamilyDetailActivity.class);
            startActivity(intent);
        }

        @Override
        public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
            return false;
        }
    });
    familyDtoCommonAdapter.notifyDataSetChanged();
    }
    /**
     * 标题的初始化
     */
    private void initToolbar() {
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initData() {
        FamilyDto familyDto = new FamilyDto();
        familyDto.setName("王翠花");
        familyDto.setRelationship("子女");
        familyDto.setAge(40);
        familyDto.setId(222);
        familyDto.setUserId(40);
        familyDtos.add(familyDto);
        familyDtos.add(familyDto);
        familyDtos.add(familyDto);
        familyDtos.add(familyDto);
        familyDtos.add(familyDto);
        familyDtos.add(familyDto);
    }
}
