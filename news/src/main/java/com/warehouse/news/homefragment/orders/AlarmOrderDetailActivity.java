package com.warehouse.news.homefragment.orders;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.warehouse.base.activity.BaseActivity;
import com.warehouse.base.widget.FaultBottomView;
import com.warehouse.base.widget.MyToolBar;
import com.warehouse.news.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AlarmOrderDetailActivity extends BaseActivity {
    MyToolBar mToolBar;
//子工单卡片,评价卡片，底部按钮
    private LinearLayout activity_alarm_sub_order_ll,alarm_order_comment_card;
    private RelativeLayout bottomView;
//头部工单号+删除
    private TextView tv_alarm_num, item_fault_detail_delete;
    //工单
    private TextView item_tv_order_number,item_tv_order_address_content,item_tv_order_emergency_type,item_tv_order_status;

    //子工单
    private TextView item_tv_order_sub_number,item_tv_order_sub_description,item_tv_order_sub_suggestion,item_tv_order_sub_update_time;
    //进度条
    private View include_do_progress_line1,include_do_progress_line2,include_do_progress_line3,include_do_progress_line4,include_do_progress_line5;
    private ImageView include_order_progress_point_one,include_order_progress_point_two,include_order_progress_point_three;
    private TextView include_do_progress_status1,include_order_progress_one_time,include_do_progress_status2,include_order_progress_two_time,include_do_progress_status3,include_order_progress_three_time;
    //评价
    private TextView include_fault_comment_degree;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_order_detail);
        initToolbar();
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initToolbar() {
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initView() {
        mToolBar = findViewById(R.id.order_detail_toolbar);
        activity_alarm_sub_order_ll = findViewById(R.id.activity_alarm_sub_order_ll);
        alarm_order_comment_card = findViewById(R.id.alarm_order_comment_card);
        bottomView = findViewById(R.id.demand_detail_bottom_view);
        tv_alarm_num = findViewById(R.id.tv_alarm_num);
        item_fault_detail_delete = findViewById(R.id.item_fault_detail_delete);
        item_tv_order_number = findViewById(R.id.item_tv_order_number);
        item_tv_order_address_content = findViewById(R.id.item_tv_order_address_content);
        item_tv_order_emergency_type = findViewById(R.id.item_tv_order_emergency_type);
        item_tv_order_status = findViewById(R.id.item_tv_order_status);
        item_tv_order_sub_number = findViewById(R.id.item_tv_order_sub_number);
        item_tv_order_sub_description = findViewById(R.id.item_tv_order_sub_description);
        item_tv_order_sub_suggestion = findViewById(R.id.item_tv_order_sub_suggestion);
        item_tv_order_sub_update_time = findViewById(R.id.item_tv_order_sub_update_time);
        include_do_progress_line1 = findViewById(R.id.include_do_progress_line1);
        include_do_progress_line2 = findViewById(R.id.include_do_progress_line2);
        include_do_progress_line3 = findViewById(R.id.include_do_progress_line3);
        include_do_progress_line4 = findViewById(R.id.include_do_progress_line4);
        include_do_progress_line5 = findViewById(R.id.include_do_progress_line5);
        include_order_progress_point_one = findViewById(R.id.include_order_progress_point_one);
        include_order_progress_point_two = findViewById(R.id.include_order_progress_point_two);
        include_order_progress_point_three = findViewById(R.id.include_order_progress_point_three);
        include_do_progress_status1 = findViewById(R.id.include_do_progress_status1);
        include_order_progress_one_time = findViewById(R.id.include_order_progress_one_time);
        include_do_progress_status2 = findViewById(R.id.include_do_progress_status2);
        include_order_progress_two_time = findViewById(R.id.include_order_progress_two_time);
        include_do_progress_status3 = findViewById(R.id.include_do_progress_status3);
        include_order_progress_three_time = findViewById(R.id.include_order_progress_three_time);
        include_fault_comment_degree = findViewById(R.id.include_fault_comment_degree);
    }

    private void setOrderSubCard() {
        activity_alarm_sub_order_ll.setVisibility(View.VISIBLE);
        item_tv_order_sub_number.setText("124463443");
        item_tv_order_sub_description.setText("描述");
        item_tv_order_sub_suggestion.setText("建议");
        item_tv_order_sub_update_time.setText("2020-12-19 12:00:00");
    }
    private void setProgressLine(int status) {
        if (status == 1) {
            include_do_progress_line1.setBackgroundResource(R.color.h_main_color);
            include_order_progress_point_one.setImageResource(R.drawable.main_color_point);
            include_do_progress_status1.setTextColor(ContextCompat.getColor(this, R.color.a));
        } else if (status == 2) {
            include_do_progress_line1.setBackgroundResource(R.color.h_main_color);
            include_order_progress_point_one.setImageResource(R.drawable.main_color_point);
            include_do_progress_status1.setTextColor(ContextCompat.getColor(this, R.color.a));
            include_do_progress_line2.setBackgroundResource(R.color.h_main_color);
            include_do_progress_line3.setBackgroundResource(R.color.h_main_color);
            include_order_progress_point_two.setImageResource(R.drawable.main_color_point);
            include_do_progress_status2.setTextColor(ContextCompat.getColor(this, R.color.a));
        } else if (status == 3) {
            include_do_progress_line1.setBackgroundResource(R.color.h_main_color);
            include_order_progress_point_one.setImageResource(R.drawable.main_color_point);
            include_do_progress_status1.setTextColor(ContextCompat.getColor(this, R.color.a));
            include_do_progress_line2.setBackgroundResource(R.color.h_main_color);
            include_do_progress_line3.setBackgroundResource(R.color.h_main_color);
            include_order_progress_point_two.setImageResource(R.drawable.main_color_point);
            include_do_progress_status2.setTextColor(ContextCompat.getColor(this, R.color.a));
            include_do_progress_line4.setBackgroundResource(R.color.h_main_color);
            include_do_progress_line5.setBackgroundResource(R.color.h_main_color);
            include_order_progress_point_three.setImageResource(R.drawable.main_color_point);
            include_do_progress_status3.setTextColor(ContextCompat.getColor(this, R.color.a));
        }
    }

    private void setOrderAndCommment() {
        tv_alarm_num.setText("123543222");
        item_tv_order_number.setText("deqfrew");
        item_tv_order_address_content.setText("地址");
        item_tv_order_emergency_type.setText("紧急程度");
        item_tv_order_status.setText("已完成");
    }
    int general_comment_mum = 0;
    private void evaluateOrderShowPop() {
        setPopWindow(R.layout.activity_evaluate_star);
        final RatingBar general_comment_rat = popview.findViewById(R.id.general_comment_rat);
        final EditText etContent = popview.findViewById(R.id.popup_bottom_engineer_score_et);
        final RadioButton rbGood = popview.findViewById(R.id.popup_bottom_engineer_score_rb_good);
        final RadioButton rbMid = popview.findViewById(R.id.popup_bottom_engineer_score_rb_mid);
        final RadioButton rbBad = popview.findViewById(R.id.popup_bottom_engineer_score_rb_bad);
        general_comment_rat.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(AlarmOrderDetailActivity.this,"总体评分="+rating,Toast.LENGTH_LONG).show();
                general_comment_mum = (int)rating;
            }
        });
        rbGood.setChecked( true );
        TextView cancle = popview.findViewById(R.id.cancel);
        Button evaluate_submit = popview.findViewById(R.id.bt_comment_submit);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispopwindow();
            }
        });
        evaluate_submit.setText("确认验收");
        evaluate_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
            }
        });
    }
}
