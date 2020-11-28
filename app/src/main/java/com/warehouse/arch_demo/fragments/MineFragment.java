package com.warehouse.arch_demo.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.warehouse.arch_demo.R;
import com.warehouse.arch_demo.activity.FamilyListActivity;
import com.warehouse.arch_demo.activity.UserInfoActivity;
import com.warehouse.base.preference.BasicDataPreferenceUtil;

import butterknife.BindView;

public class MineFragment extends Fragment implements View.OnClickListener {
   // @BindView(R.id.txt_username)
    TextView mTxtUserName;
 //   @BindView(R.id.my_info)
    TextView my_info;
 //   @BindView(R.id.my_family)
    TextView my_family;
 //   @BindView(R.id.my_setting)
    TextView my_setting;
  //  @BindView(R.id.btn_logout)
    Button mbtnLogout;

    private View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        mView = inflater.inflate(R.layout.fragment_mine, null);
        initView();
        return mView;
    }

    private void initView() {
        mTxtUserName = mView.findViewById(R.id.txt_username);
        my_info = mView.findViewById(R.id.my_info);
        my_family = mView.findViewById(R.id.my_family);
        my_setting = mView.findViewById(R.id.my_setting);
        mbtnLogout = mView.findViewById(R.id.btn_logout);
        mTxtUserName.setText(BasicDataPreferenceUtil.getInstance().getString("LoginName","1"));
        my_info.setOnClickListener(this);
        my_family.setOnClickListener(this);
        my_setting.setOnClickListener(this);
        mbtnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_info:
                Intent intent = new Intent(getActivity(), UserInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.my_family:
                Intent intent1 = new Intent(getActivity(), FamilyListActivity.class);
                startActivity(intent1);
                break;
            case R.id.my_setting:
                break;
            case R.id.btn_logout:
                break;
                default:
                    break;
        }
    }
}
