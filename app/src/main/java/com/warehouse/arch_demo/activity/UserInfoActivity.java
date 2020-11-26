package com.warehouse.arch_demo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.warehouse.arch_demo.R;
import com.warehouse.base.activity.BaseActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserInfoActivity extends BaseActivity {
    private static final int ALBUM_OK = 3432; // 选择相册后的标记
    private static final int CUT_OK = 0x0013; // 裁剪的标记
    private static final int CAMERA_REQUEST = 634434; // 拍照后的标记

    private File tempFile;
    @BindView(R.id.user_logo)
    CircleImageView mImageHead;
    @BindView(R.id.user_phone)
    EditText user_phone;
    @BindView(R.id.user_name)
    EditText user_name;
    @BindView(R.id.user_sex)
    TextView user_sex;
    @BindView(R.id.user_born_date)
    TextView user_born_date;
    @BindView(R.id.user_from)
    TextView user_from;
    @BindView(R.id.submit_user_info)
    Button submit_user_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_info);

        super.onCreate(savedInstanceState);
    }

    @OnClick()
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.user_logo://头像
                showDialog();
                break;
            case R.id.user_sex://性别
                break;
            case R.id.user_born_date://出生日期
                break;
            case R.id.user_from://居住地
                break;
            case R.id.submit_user_info://提交信息
                break;
                default:
                    break;
        }
    }

    private void showUser() {

    }
    private void showDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);

        // 自定义布局 View
        View dialogView = View.inflate(this, R.layout.photo_choose_dialog, null);

        dialog.setContentView(dialogView);

        // 动画
        Window window = dialog.getWindow();
        // 设置动画
        window.setWindowAnimations(R.style.main_menu_animstyle);

        // 固定在底部
        window.setGravity(Gravity.BOTTOM);

        // 宽度全屏
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        // 处理点击事件 取消
        Button cancelBt = dialogView.findViewById(R.id.user_cancel);
        cancelBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); // 关闭
            }
        });

        // 处理点击事件 选择相册
        Button imageDepotBt = dialogView.findViewById(R.id.image_depot);
        imageDepotBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 隐士意图 激活系统的东西
                Intent albumIntent = new Intent(Intent.ACTION_PICK);
                // 匹配人家系统的类型
                albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                // 选择相册，用户选择的结果告诉我们
                startActivityForResult(albumIntent, ALBUM_OK);
                dialog.dismiss(); // 关闭
            }
        });

        // TODO 其他手机OK的，目前华为手机有问题
        Button photoCamre = dialogView.findViewById(R.id.photo_camre);
        photoCamre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动系统的隐士意图去拍照   --- 相机应用 暴露 标记：android.media.action.IMAGE_CAPTURE
                Intent getImageByCamera =  new Intent("android.media.action.IMAGE_CAPTURE");
                // 调用系统拍照完成之后的照片， 先存放在本地（后续的考虑  上传到真实服务器的考虑的）
                getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
                startActivityForResult(getImageByCamera, CAMERA_REQUEST); // 拍照完成之后，还需要拿到拍照后的图片图片
                dialog.cancel(); // 隐藏框先
            }
        });

        // 显示
        dialog.show();
    }
}
