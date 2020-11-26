package com.warehouse.arch_demo.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

import com.cebserv.gcs.anancustom.app.DigitalApp;

/**
 * Created by Administrator on 2016/12/9.
 */

public class DensityUtil {

    public static  int[] getMyWindowDisplay(Activity context){

        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        int a[]=new int[2];
        a[0]=width;
        a[1]=height;
        return a;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = DigitalApp.context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = DigitalApp.context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
