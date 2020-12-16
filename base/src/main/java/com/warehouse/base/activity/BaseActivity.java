package com.warehouse.base.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.warehouse.base.R;
import com.warehouse.base.utils.ActivityManager;
import com.warehouse.base.utils.Global;


public abstract class BaseActivity extends AppCompatActivity {
    //获取TAG的activity名称
    protected final String TAG = this.getClass().getSimpleName();
    //是否显示标题栏
    private boolean isShowTitle = true;
    //是否显示状态栏
    private boolean isShowStatusBar = true;
    //是否允许旋转屏幕
    private boolean isAllowScreenRoate = false;
    //封装Toast对象
    private static Toast toast;
    //   public Context context;
    private PopupWindow pw;
    protected PopupWindow needPhotopop;
    private int from = 0;
    public View popview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  context = this;
        //activity管理
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActivityManager.getInstance().addActivity(this);
        if (isAllowScreenRoate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        Log.v(TAG, "---------onStart()");

    }
    /**
     * 是否允许屏幕旋转
     *
     * @param allowScreenRoate true or false
     */
    public void setAllowScreenRoate(boolean allowScreenRoate) {
        isAllowScreenRoate = allowScreenRoate;
    }
    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }
    /**
     * android 5.0 及以下沉浸式状态栏
     */
    protected void setStatusBar() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//设置透明状态栏
        }

        initSystemBar(this);

    }
    /**
     * 沉浸式状态栏.
     */
    public void initSystemBar(Activity activity) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(activity, true);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setStatusBarTintResource(R.color.colorPrimary);

    }
    private void setTranslucentStatus(Activity activity, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;

        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //activity管理
        ActivityManager.getInstance().finishActivity(this);
        Log.v(TAG, "---------onDestroy()");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.v(TAG, "---------onRestart()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(TAG, "---------onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "---------onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(TAG, "---------onPause()");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.v(TAG, "---------onStop()");
    }
    public enum Location {BOTTOM}
    public void setPopWindow(int layout) {

        from = Location.BOTTOM.ordinal();
        popview = getLayoutInflater().inflate(layout, null);
//内容，高度，宽度
        if (Location.BOTTOM.ordinal() == from) {
            needPhotopop = new PopupWindow(popview, ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        } else {
            needPhotopop = new PopupWindow(popview, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.FILL_PARENT, true);
        }
        //动画效果
        if (Location.BOTTOM.ordinal() == from) {
            needPhotopop.setAnimationStyle(R.style.AnimationBottomFade);
        }
        //菜单背景色
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        needPhotopop.setBackgroundDrawable(dw);
        //宽度
        //popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
        //高度
        //popupWindow.setHeight(LayoutParams.FILL_PARENT);
        //显示位置
        if (Location.BOTTOM.ordinal() == from) {
            needPhotopop.showAtLocation(getLayoutInflater().inflate(R.layout.activity_bigimagepager, null), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
        //设置背景半透明
        backgroundAlpha(0.5f);

        //关闭事件
        needPhotopop.setOnDismissListener(new popupDismissListener());

        popview.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                /*if( popupWindow!=null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow=null;
                }*/
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
                return false;
            }
        });
    }
    /**
     * 设置添加屏幕的
     * +
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     */
    class popupDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            if (Global.popWinShow) {
                backgroundAlpha(0.5f);
            } else {
                backgroundAlpha(1f);
            }
        }
    }
    protected void dispopwindow() {
        needPhotopop.dismiss();
    }
}
