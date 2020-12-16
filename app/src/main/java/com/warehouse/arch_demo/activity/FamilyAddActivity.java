package com.warehouse.arch_demo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.warehouse.arch_demo.R;
import com.warehouse.base.activity.BaseActivity;

import butterknife.ButterKnife;

public class FamilyAddActivity extends BaseActivity {
 private Button button;
 private EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_add);
        ButterKnife.bind(this);
    button = findViewById(R.id.submit_user_name);
        editText = findViewById(R.id.family_add_tel);
    button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            subInfo();
        }
    });
    }

    private void subInfo() {
        String num = editText.getText().toString();

    }
}
