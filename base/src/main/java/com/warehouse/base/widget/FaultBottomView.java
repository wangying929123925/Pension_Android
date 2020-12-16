package com.warehouse.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import com.warehouse.base.R;

public class FaultBottomView extends RelativeLayout {

    public RelativeLayout mLeftOneShadow;  //左一
    public RelativeLayout mRightParentShadow;  //  右一先生
    public RelativeLayout mRightShadow;
    public RelativeLayout mLeftTwoShadow;
    public TextView mLeftOneTxt; //左一
    public TextView mLeftTwoTxt;  //左二（中间）
    public TextView mRightTxt;  //又二（中间）
    public TextView mParentRightTxt;  //右一
//    public ShadowView mFavShadow;
//    private ImageView mFavIcon;
//    private TextView mFavText;

    public FaultBottomView(Context context) {
        super(context);
    }

    public FaultBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.include_bottom_order_view, this);
        mLeftOneShadow =  findViewById(R.id.fault_left_one_shadow);
        mRightParentShadow =  findViewById(R.id.right_parent_shadow);  //右一
        mRightShadow =  findViewById(R.id.right_shadow);
        mLeftTwoShadow =  findViewById(R.id.left_two_shadow);
        mLeftOneTxt = (TextView) findViewById(R.id.include_bot_demand_fault_left_one);  // 左一
        mLeftTwoTxt = (TextView) findViewById(R.id.include_bot_demand_left_two);
        mRightTxt = (TextView) findViewById(R.id.include_bot_demand_right);  // 右二
        mParentRightTxt = (TextView) findViewById(R.id.include_bot_demand_parent_right);  // 右一
        setLeftOneVisible(true);
    }

    public FaultBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setLeftOneVisible(boolean visible) {
        if (visible) {
            mLeftOneShadow.setVisibility(View.VISIBLE);
        } else {
            mLeftOneShadow.setVisibility(View.INVISIBLE);
        }
    }

    public void setLeftTwoVisible(boolean visible) {
        if (visible) {
            mLeftTwoShadow.setVisibility(View.VISIBLE);
        } else {
            mLeftTwoShadow.setVisibility(View.INVISIBLE);
        }
    }

    //    右一先生
    public void setRightParentVisible(boolean visible) {
        if (visible) {
            mRightParentShadow.setVisibility(View.VISIBLE);
        } else {
            mRightParentShadow.setVisibility(View.INVISIBLE);
        }
    }
//右二
    public void setRightVisible(boolean visible) {
        if (visible) {
            mRightShadow.setVisibility(View.VISIBLE);
        } else {
            mRightShadow.setVisibility(View.INVISIBLE);
        }
    }

    public void setLeftOneText(String resId) {
        mLeftOneTxt.setText(resId);
    }

    public void setLeftTwoText(String resId) {
        mLeftTwoTxt.setText(resId);
    }

    public void setRightParentText(String resId) {
        mParentRightTxt.setText(resId);
    }

    public void setRightText(String resId) {
        mRightTxt.setText(resId);
    }
}
