package com.warehouse.video;

import android.Manifest;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * @author Lance
 * @date 2018/9/7
 */
public class PlayActivity extends RxAppCompatActivity implements SurfaceHolder.Callback {
    private VideoPlayer player;
    public String path;
    private SurfaceView surfaceView;
    private String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager
                .LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_play);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
        requestPower();
        player = new VideoPlayer();
        player.setOnPrepareListener(new VideoPlayer.OnPrepareListener() {
            @Override
            public void onPrepared() {
                player.start();
            }
        });
       // path = getIntent().getStringExtra("path");
        player.setDataSource("/storage/emulated/0/DCIM/test.flv");
//        player.setDataSource(path);
//        player.setDataSource("http://pili-live-hdl.qhmywl.com/dsdtv/891dfa401663622b29ab70c16ea2273e.flv");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        surfaceView.getHolder().removeCallback(this);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                    .LayoutParams.FLAG_FULLSCREEN);
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_play);
        surfaceView = findViewById(R.id.surfaceView);
        surfaceView.getHolder().addCallback(this);
      //  player.setDataSource(path);
        player.setDataSource("/storage/emulated/0/DCIM/test.flv");
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.prepare();
    }

    @Override
    protected void onPause() {
        super.onPause();
      //  player.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        player.setSurface(holder.getSurface());
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    public void requestPower() {
        //判断是否已经赋予权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this,
                permissions[0]) != PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
